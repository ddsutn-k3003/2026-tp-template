package ar.edu.utn.dds.k3003.catedra.donaciones;

import static org.mockito.Mockito.*;

import ar.edu.utn.dds.k3003.Fachada;
import ar.edu.utn.dds.k3003.catedra.ClassFinder;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.DonacionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.EstadoDonacionEnum;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.DonadorDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.QuejaDTO;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaDonaciones;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaDonadoresYEntidades;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaLogistica;
import java.time.LocalDate;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@EnabledIf("ar.edu.utn.dds.k3003.catedra.donaciones.DonacionesTest#condicion")
public class DonacionesTest {

  FachadaDonaciones instancia;
  @Mock FachadaDonadoresYEntidades fachadaDonadoresYEntidades;
  @Mock FachadaLogistica fachadaLogistica;

  DonadorDTO donadorEjemplo;
  DonacionDTO donacionEjemplo;
  DonacionDTO donacionAceptadaEjemplo;
  QuejaDTO quejaEjemplo;

  @SneakyThrows
  @BeforeEach
  void setUp() {

    var clazz = ClassFinder.findClass();
    instancia = (FachadaDonaciones) clazz.getDeclaredConstructor().newInstance();

    instancia.setFachadaDonadoresYEntidades(fachadaDonadoresYEntidades);
    instancia.setFachadaLogistica(fachadaLogistica);

    donacionEjemplo = new DonacionDTO(null, "donador1", "deposito1", "descripcion1", "producto1", 5, EstadoDonacionEnum.INGRESADA);
    donacionAceptadaEjemplo =
        new DonacionDTO(null, "donador1", "deposito1", "descripcion1", "producto1", 5, EstadoDonacionEnum.ACEPTADA);
    donadorEjemplo =
        new DonadorDTO("donador1", "donador1", "donador1", 5, "donador1", "donador1", "donador1", null, "donador1");
    quejaEjemplo = new QuejaDTO(null, "donacion1", "donador1", null, "descripcion1");
  }

  static boolean condicion() {

    return FachadaDonaciones.class.isAssignableFrom(Fachada.class);
  }

  @Test
  void testRegistrarDonacion() {

    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
        .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
        .thenReturn(Boolean.TRUE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());

    DonacionDTO retorno = instancia.registrarDonacion(donacionEjemplo);

    DonacionDTO busqueda = instancia.buscarDonacionPorID(retorno.id());

    Assertions.assertNotNull(retorno.id());
    Assertions.assertEquals(retorno.id(), busqueda.id());

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
  }

  @Test
  void testRegistrarDonacionFallido() {

    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
            .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
            .thenReturn(Boolean.TRUE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.registrarDonacion(null);
        });

    DonacionDTO retorno = instancia.registrarDonacion(donacionEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.registrarDonacion(retorno);
        });

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
  }

  @Test
  void testRegistrarDonacionNoPuedeDonar() {

    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
            .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
            .thenReturn(Boolean.FALSE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.registrarDonacion(donacionEjemplo);
        });

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
  }

  @Test
  void testBuscarDonacionPorIDFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.buscarDonacionPorID("Inexistente");
        });
  }

  @Test
  void testRegistrarQueja() {

    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
            .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
            .thenReturn(Boolean.TRUE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());
    when(fachadaDonadoresYEntidades.agregarQueja(quejaEjemplo)).thenReturn(any());

    DonacionDTO retornoD = instancia.registrarDonacion(donacionEjemplo);

    DonacionDTO retorno =
        instancia.registrarQuejaEnDonacion(retornoD.id(), quejaEjemplo.descripcion());

    Assertions.assertNotNull(retorno.id());
    Assertions.assertEquals(
            EstadoDonacionEnum.CONQUEJA, instancia.buscarDonacionPorID(retorno.id()).estado());

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
    verify(fachadaDonadoresYEntidades, times(1)).agregarQueja(quejaEjemplo);
  }

  @Test
  void testRegistrarQuejaFallido() {
    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
            .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
            .thenReturn(Boolean.TRUE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());
    when(fachadaDonadoresYEntidades.agregarQueja(any())).thenThrow(new RuntimeException());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.registrarQuejaEnDonacion(null, quejaEjemplo.descripcion());
        });

    DonacionDTO retorno = instancia.registrarDonacion(donacionEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.registrarQuejaEnDonacion(retorno.id(), quejaEjemplo.descripcion());
        });
    Assertions.assertEquals(
            EstadoDonacionEnum.ACEPTADA, instancia.buscarDonacionPorID(retorno.id()).estado());

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
    verify(fachadaDonadoresYEntidades, times(1)).agregarQueja(quejaEjemplo);
  }

  @Test
  void testCambiarEstadoDeDonacion() {

    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
            .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
            .thenReturn(Boolean.TRUE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());

    DonacionDTO retorno = instancia.registrarDonacion(donacionEjemplo);

    DonacionDTO actualizada =
        instancia.cambiarEstadoDeDonacion(retorno.id(), EstadoDonacionEnum.ACEPTADA);

    Assertions.assertNotNull(actualizada);
    Assertions.assertEquals(EstadoDonacionEnum.ACEPTADA, actualizada.estado());
    Assertions.assertEquals(
            EstadoDonacionEnum.ACEPTADA, instancia.buscarDonacionPorID(retorno.id()).estado());

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
  }

  @Test
  void testCambiarEstadoDeDonacionFallido() {

    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
            .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
            .thenReturn(Boolean.TRUE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.cambiarEstadoDeDonacion("Inexistente", EstadoDonacionEnum.ACEPTADA);
        });

    DonacionDTO retorno = instancia.registrarDonacion(donacionEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.cambiarEstadoDeDonacion(retorno.id(), null);
        });

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
  }

  @Test
  void testBuscarPorDonadorYFechaInicio() {

    when(fachadaDonadoresYEntidades.buscarDonadorPorID(donadorEjemplo.id()))
            .thenReturn(donadorEjemplo);
    when(fachadaDonadoresYEntidades.puedeDonar(donadorEjemplo.id()))
            .thenReturn(Boolean.TRUE);
    when(fachadaLogistica.gestionarDonacion(any(), any(), anyInt())).thenReturn(any());

    DonacionDTO retorno = instancia.registrarDonacion(donacionEjemplo);

    List<DonacionDTO> resultado =
        instancia.buscarPorDonadorYFechaInicio(retorno.donadorID(), LocalDate.ofYearDay(2025, 99));

    Assertions.assertNotNull(resultado);
    Assertions.assertTrue(resultado.stream().anyMatch(d -> d.id().equals(retorno.id())));
    Assertions.assertEquals(1, resultado.size());

    verify(fachadaDonadoresYEntidades, times(1)).puedeDonar(donadorEjemplo.id());
    verify(fachadaDonadoresYEntidades, times(1)).buscarDonadorPorID(donadorEjemplo.id());
    verify(fachadaLogistica, times(1)).gestionarDonacion(any(), any(), anyInt());
  }

  @Test
  void testBuscarPorDonadorYFechaInicioFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.buscarPorDonadorYFechaInicio("Inexistente", LocalDate.now());
        });
  }
}
