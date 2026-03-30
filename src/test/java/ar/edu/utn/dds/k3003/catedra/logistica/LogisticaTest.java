package ar.edu.utn.dds.k3003.catedra.logistica;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import ar.edu.utn.dds.k3003.Fachada;
import ar.edu.utn.dds.k3003.catedra.ClassFinder;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.NecesidadMaterialDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.ProductoSolicitadoDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.*;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaDonaciones;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaDonadoresYEntidades;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaLogistica;
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
@EnabledIf("ar.edu.utn.dds.k3003.catedra.logistica.LogisticaTest#condicion")
public class LogisticaTest {

  FachadaLogistica instancia;
  @Mock FachadaDonadoresYEntidades fachadaDonadoresYEntidades;
  @Mock FachadaDonaciones fachadaDonaciones;

  DepositoDTO depositoEjemplo;
  NecesidadDeEntidadDTO necesidadDeEjemplo;
  PaqueteDTO paqueteEjemplo;

  @SneakyThrows
  @BeforeEach
  void setUp() {

    var clazz = ClassFinder.findClass();
    instancia = (FachadaLogistica) clazz.getDeclaredConstructor().newInstance();
    instancia.setAlgoritmoMM();

    instancia.setFachadaDonadoresYEntidades(fachadaDonadoresYEntidades);
    instancia.setFachadaDonaciones(fachadaDonaciones);

    depositoEjemplo = new DepositoDTO(null, "Deposito1", "direc1", 1000, null);
    necesidadDeEjemplo =
        new NecesidadDeEntidadDTO(null, "entidad1", 5, "necesidadUrgente", 10, "producto1");
    paqueteEjemplo = new PaqueteDTO("1", "producto1", "producto1", 10);
  }

  static boolean condicion() {

    return FachadaLogistica.class.isAssignableFrom(Fachada.class);
  }

  @Test
  void testAgregarDeposito() {

    DepositoDTO retorno = instancia.agregarDeposito(depositoEjemplo);

    Assertions.assertNotNull(retorno.id());
    Assertions.assertEquals(retorno.nombre(), depositoEjemplo.nombre());
  }

  @Test
  void testAgregarDepositoFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarDeposito(null);
        });

    DepositoDTO retorno = instancia.agregarDeposito(depositoEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarDeposito(retorno);
        });
  }

  @Test
  void testBuscarDepositoPorID() {
    DepositoDTO retorno = instancia.agregarDeposito(depositoEjemplo);

    DepositoDTO buscado = instancia.buscarDepositoPorID(retorno.id());

    Assertions.assertNotNull(buscado);
    Assertions.assertEquals(retorno.id(), buscado.id());
  }

  @Test
  void testBuscarDepositoPorIDFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.buscarDepositoPorID("Inexistente");
        });
  }

  @Test
  void testGestionarDonacion() {

    when(fachadaDonadoresYEntidades.obtenerNecesidadesInsatisfechasDe(
            new ProductoSolicitadoDTO("p1", "p1", "p1")))
        .thenReturn(List.of(new NecesidadMaterialDTO("1", "e1", 5, "neceDescr", 5, "producto1")));

    DepositoDTO depositoRetorno = instancia.agregarDeposito(depositoEjemplo);

    DepositoDTO actualizado = instancia.gestionarDonacion(depositoRetorno.id(), "producto1", 10);

    Assertions.assertNotNull(actualizado);
    Assertions.assertTrue(actualizado.id().equals(depositoRetorno.id()));

    verify(fachadaDonadoresYEntidades, times(1))
        .obtenerNecesidadesInsatisfechasDe(new ProductoSolicitadoDTO("p1", "p1", "p1"));
  }

  @Test
  void testGestionarDonacionFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.gestionarDonacion("Inexistente", "producto1", 10);
        });

    DepositoDTO depositoRetorno = instancia.agregarDeposito(depositoEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.gestionarDonacion(depositoRetorno.id(), "producto1", -1);
        });

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.gestionarDonacion(depositoRetorno.id(), "producto1", 0);
        });
  }

  @Test
  void testEjecutarMatchmaking() {

    List<NecesidadDeEntidadDTO> necesidades =
        List.of(new NecesidadDeEntidadDTO("n1", "e1", 5, "necDescr", 5, "producto1"));

    AsignacionDTO asignacion = instancia.ejecutarMatchmaking(paqueteEjemplo, necesidades);

    Assertions.assertNotNull(asignacion);
    Assertions.assertEquals(paqueteEjemplo.id(), asignacion.paqueteID());
    Assertions.assertEquals(necesidades.getFirst().id(), asignacion.necesidadID());
  }

  @Test
  void testEjecutarMatchmakingFallido() {

    List<NecesidadDeEntidadDTO> necesidades =
        List.of(new NecesidadDeEntidadDTO("n1", "e1", 5, "necDescr", 5, "producto1"));

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.ejecutarMatchmaking(null, necesidades);
        });
  }

  @Test
  void testReportarEntrega() {

    instancia.reportarEntrega(paqueteEjemplo);

    Assertions.assertTrue(
        instancia
            .buscarAsignacionPorPaqueteID("1")
            .estado()
            .equals(EstadoAsginacionEnum.COMPLETADA));

    verify(fachadaDonaciones, times(1)).cambiarEstadoDeDonacion("a", any());
  }

  @Test
  void testReportarEntregaFallido() {

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.reportarEntrega(null);
        });

    when(fachadaDonaciones.cambiarEstadoDeDonacion("d1", any())).thenThrow(new RuntimeException());

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.reportarEntrega(paqueteEjemplo);
        });

    verify(fachadaDonaciones, times(1)).cambiarEstadoDeDonacion("d1", any());
  }
}
