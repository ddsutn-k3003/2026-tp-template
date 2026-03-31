package ar.edu.utn.dds.k3003.catedra.dtos.logistica;

public record NecesidadDeEntidadDTO(
    String id,
    String entidadID,
    Integer nivelDeUrgencia,
    String descripcion,
    Integer cantidadObjetivo,
    String productoSolicitado) {}
