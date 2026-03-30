package ar.edu.utn.dds.k3003.catedra.donadoresyentidades;

import static org.mockito.Mockito.*;

import ar.edu.utn.dds.k3003.Fachada;
import ar.edu.utn.dds.k3003.catedra.ClassFinder;
import ar.edu.utn.dds.k3003.catedra.dtos.donadoresYEntidades.*;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.InsigniaDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.incentivos.MisionDTO;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaDonadoresYEntidades;
import ar.edu.utn.dds.k3003.catedra.fachadas.FachadaIncentivos;
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
@EnabledIf("ar.edu.utn.dds.k3003.catedra.donadoresyentidades.DonadoresYEntidadesTest#condicion")
public class DonadoresYEntidadesTest {

  FachadaDonadoresYEntidades instancia;
  @Mock FachadaIncentivos fachadaIncentivos;

  DonadorDTO donadorEjemplo;
  EntidadBeneficaDTO entidadEjemplo;
  NecesidadMaterialDTO necesidadEjemplo;
  QuejaDTO quejaEjemplo;

  @SneakyThrows
  @BeforeEach
  void setUp() {

    var clazz = ClassFinder.findClass();
    instancia = (FachadaDonadoresYEntidades) clazz.getDeclaredConstructor().newInstance();

    instancia.setFachadaIncentivos(fachadaIncentivos);

    donadorEjemplo =
        new DonadorDTO(null, "d1", "d1", 5, "d1", "d1", "d1", EstadoDonadorEnum.VERIFICADO, "d1");
    entidadEjemplo = new EntidadBeneficaDTO(null, "e1", "e1", "e1", "e1");
    necesidadEjemplo = new NecesidadMaterialDTO(null, "n1", 5, "n1", 5, "p1");
    quejaEjemplo = new QuejaDTO(null, "donacion1", donadorEjemplo.id(), null, "q1");
  }

  static boolean condicion() {

    return FachadaDonadoresYEntidades.class.isAssignableFrom(Fachada.class);
  }

  @Test
  void testAgregarDonador() {

    DonadorDTO retorno = instancia.agregarDonador(donadorEjemplo);

    Assertions.assertNotNull(retorno.id());
    Assertions.assertEquals(donadorEjemplo.nombre(), retorno.nombre());
    Assertions.assertEquals(retorno.nroDocumento(), donadorEjemplo.nroDocumento());
  }

  @Test
  void testAgregarDonadorFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarDonador(null);
        });

    DonadorDTO retorno = instancia.agregarDonador(donadorEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarDonador(retorno);
        });
  }

  @Test
  void testBuscarDonadorPorID() {
    DonadorDTO retorno = instancia.agregarDonador(donadorEjemplo);

    DonadorDTO buscado = instancia.buscarDonadorPorID(retorno.id());

    Assertions.assertNotNull(buscado);
    Assertions.assertEquals(retorno.id(), buscado.id());
  }

  @Test
  void testBuscarDonadorPorIDFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.buscarDonadorPorID("Inexistente");
        });
  }

  @Test
  void testAgregarEntidad() {
    EntidadBeneficaDTO retorno = instancia.agregarEntidad(entidadEjemplo);

    Assertions.assertNotNull(retorno.id());
    Assertions.assertEquals(entidadEjemplo.razonSocial(), retorno.razonSocial());
  }

  @Test
  void testAgregarEntidadFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarEntidad(null);
        });

    EntidadBeneficaDTO retorno = instancia.agregarEntidad(entidadEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarEntidad(retorno);
        });
  }

  @Test
  void testBuscarEntidadPorID() {
    EntidadBeneficaDTO retorno = instancia.agregarEntidad(entidadEjemplo);

    EntidadBeneficaDTO buscada = instancia.buscarEntidadPorID(retorno.id());

    Assertions.assertNotNull(buscada);
    Assertions.assertEquals(retorno.id(), buscada.id());
  }

  @Test
  void testBuscarEntidadPorIDFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.buscarEntidadPorID("Inexistente");
        });
  }

  @Test
  void testRegistrarNecesidad() {

    NecesidadMaterialDTO retorno = instancia.registrarNecesidad(necesidadEjemplo);

    Assertions.assertNotNull(retorno.id());
    Assertions.assertEquals(necesidadEjemplo.entidadID(), retorno.entidadID());
    Assertions.assertEquals(
        necesidadEjemplo.productoSolicitadoID(), retorno.productoSolicitadoID());
    Assertions.assertEquals(necesidadEjemplo.cantidadObjetivo(), retorno.cantidadObjetivo());
  }

  @Test
  void testRegistrarNecesidadFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.registrarNecesidad(null);
        });

    NecesidadMaterialDTO retorno = instancia.registrarNecesidad(necesidadEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.registrarNecesidad(retorno);
        });
  }

  @Test
  void testAgregarQueja() {

    instancia.agregarDonador(donadorEjemplo);

    QuejaDTO retorno = instancia.agregarQueja(quejaEjemplo);

    Assertions.assertNotNull(retorno.id());
    Assertions.assertEquals(
        instancia.obtenerQuejasDe(quejaEjemplo.donadorID()).getFirst().id(), retorno.id());
  }

  @Test
  void testAgregarQuejaFallido() {

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarQueja(null);
        });

    QuejaDTO retorno = instancia.agregarQueja(quejaEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.agregarQueja(retorno);
        });
  }

  @Test
  void testPuedeDonar() {

    instancia.agregarDonador(donadorEjemplo);

    Boolean resultado = instancia.puedeDonar(donadorEjemplo.id());

    Assertions.assertTrue(resultado);
  }

  @Test
  void testPuedeDonarFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.puedeDonar("Inexistente");
        });
  }

  @Test
  void testNoPuedeDonar() {

    DonadorDTO retorno = instancia.agregarDonador(donadorEjemplo);

    instancia.modificarEstado(retorno.id(), EstadoDonadorEnum.BANEADO);
    Assertions.assertFalse(instancia.puedeDonar(retorno.id()));
  }

  @Test
  void testObtenerQuejasDe() {

    instancia.agregarDonador(donadorEjemplo);

    instancia.agregarQueja(quejaEjemplo);

    List<QuejaDTO> resultado = instancia.obtenerQuejasDe(donadorEjemplo.id());

    Assertions.assertNotNull(resultado);
    Assertions.assertEquals(1, resultado.size());
    Assertions.assertEquals(quejaEjemplo.id(), resultado.getFirst().id());
  }

  @Test
  void testObtenerQuejasDeFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.obtenerQuejasDe("Inexistente");
        });
  }

  @Test
  void testModificarEstadoFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.modificarEstado("Inexistente", EstadoDonadorEnum.BANEADO);
        });

    instancia.agregarDonador(donadorEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.modificarEstado(donadorEjemplo.id(), null);
        });
  }

  @Test
  void testModificarCategoria() {

    DonadorDTO retorno = instancia.agregarDonador(donadorEjemplo);
    DonadorDTO actualizado = instancia.modifcarCategoria(retorno.id(), "CategoriaNueva");

    Assertions.assertNotNull(actualizado);
    Assertions.assertEquals(
        "CategoriaNueva", instancia.buscarDonadorPorID(actualizado.id()).categoria());
  }

  @Test
  void testModificarCategoriaFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.modifcarCategoria("Inexistente", "Categoria1");
        });

    instancia.agregarDonador(donadorEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.modifcarCategoria(donadorEjemplo.id(), null);
        });
  }

  @Test
  void testObtenerNecesidadesInsatisfechasDe() {
    instancia.registrarNecesidad(necesidadEjemplo);

    List<NecesidadMaterialDTO> resultado =
        instancia.obtenerNecesidadesInsatisfechasDe(new ProductoSolicitadoDTO("p1", "p1", "p1"));

    Assertions.assertNotNull(resultado);
    Assertions.assertEquals(1, resultado.size());
    Assertions.assertEquals(necesidadEjemplo.id(), resultado.getFirst().id());
  }

  @Test
  void testSatisfacerNecesidadFallido() {
    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.satisfacerNecesidad("Inexistente", 5);
        });

    instancia.registrarNecesidad(necesidadEjemplo);

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.satisfacerNecesidad(necesidadEjemplo.id(), 0);
        });

    Assertions.assertThrows(
        RuntimeException.class,
        () -> {
          instancia.satisfacerNecesidad(necesidadEjemplo.id(), -1);
        });
  }

  @Test
  void testObtenerEstadisticasDonador() {

    when(fachadaIncentivos.getInsigniasDeDonador(anyString()))
        .thenReturn(List.of(new InsigniaDTO(null, "insignia1", "insignia1descr")));
    when(fachadaIncentivos.getMisionEnCursoDeDonador(anyString()))
        .thenReturn(new MisionDTO(null, "mision1", "1", null, null));

    instancia.agregarDonador(donadorEjemplo);
    DonadorStatsDTO retorno = instancia.estadisticasDonador(donadorEjemplo.id());

    Assertions.assertNotNull(retorno);
    Assertions.assertEquals(donadorEjemplo.nombre(), retorno.nombre());
    Assertions.assertEquals(retorno.insigniasID().size(), 1);
    Assertions.assertNotNull(retorno.misionActualID());

    verify(fachadaIncentivos, times(1)).getInsigniasDeDonador(anyString());
    verify(fachadaIncentivos, times(1)).getMisionEnCursoDeDonador(anyString());
  }
}
