package ar.edu.utn.dds.k3003.catedra.fachadas;

import ar.edu.utn.dds.k3003.catedra.dtos.logistica.AsignacionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.DepositoDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.NecesidadDeEntidadDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.logistica.PaqueteDTO;
import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaLogistica {

  DepositoDTO agregarDeposito(DepositoDTO deposito);

  DepositoDTO buscarDepositoPorID(String depositoID) throws NoSuchElementException;

  AsignacionDTO buscarAsignacionPorPaqueteID(String paqueteID) throws NoSuchElementException;

  DepositoDTO gestionarDonacion(String depositoID, String productoID, Integer cantidad)
      throws NoSuchElementException;

  void setAlgoritmoMM();

  AsignacionDTO ejecutarMatchmaking(PaqueteDTO paqueteDTO, List<NecesidadDeEntidadDTO> depositoDTO);

  void reportarEntrega(PaqueteDTO paqueteDTO);

  void setFachadaDonadoresYEntidades(FachadaDonadoresYEntidades fachadaDonadoresYEntidades);

  void setFachadaDonaciones(FachadaDonaciones fachadaDonaciones);
}
