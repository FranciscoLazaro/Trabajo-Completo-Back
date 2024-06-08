package com.example.Service;

import com.example.Entity.Instrumento;

import java.io.IOException;
import java.util.Date;

public interface ReporteService {
    byte[] generarReporteExcel(Date fechaDesde, Date fechaHasta) throws IOException;
    byte[] generateInstrumentoPdf(Long instrumentoId) throws IOException;

}
