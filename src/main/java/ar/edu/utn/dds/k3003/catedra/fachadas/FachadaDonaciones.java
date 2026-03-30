package ar.edu.utn.dds.k3003.catedra.fachadas;

import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.DonacionDTO;
import ar.edu.utn.dds.k3003.catedra.dtos.donaciones.EstadoDonacionEnum;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public interface FachadaDonaciones {

  DonacionDTO registrarDonacion(DonacionDTO donacionDTO);

  DonacionDTO buscarDonacionPorID(String donacionID) throws NoSuchElementException;

  DonacionDTO cambiarEstadoDeDonacion(String donacionID, EstadoDonacionEnum estado)
      throws NoSuchElementException;

  List<DonacionDTO> buscarPorDonadorYFechaInicio(String donadorID, LocalDate fecha)
      throws NoSuchElementException;

  DonacionDTO registrarQuejaEnDonacion(String donacionID, String descripcion);

  void setFachadaDonadoresYEntidades(FachadaDonadoresYEntidades fachadaDonadoresYEntidades);

  void setFachadaLogistica(FachadaLogistica fachadaLogistica);
}
