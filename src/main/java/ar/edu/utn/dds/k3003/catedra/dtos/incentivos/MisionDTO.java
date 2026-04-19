package ar.edu.utn.dds.k3003.catedra.dtos.incentivos;

import ar.edu.utn.dds.k3003.catedra.dtos.logistica.TipoAlgoritmoEnum;

public record MisionDTO(
        String id,
        String nombre,
        String insigniaID,
        CategoriaDonadorEnum categoriaInicio,
        CategoriaDonadorEnum categoriaFin,
        TipoMisionEnum tipo) {}
