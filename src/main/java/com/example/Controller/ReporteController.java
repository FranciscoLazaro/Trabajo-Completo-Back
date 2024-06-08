package com.example.Controller;

import com.example.Service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;

@RestController
@RequestMapping("/reporte")
@CrossOrigin("*")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    // http://localhost:8080/reporte/excel?fechaDesde=2024-01-01&fechaHasta=2024-06-30
    @GetMapping("/excel")
    public ResponseEntity<byte[]> generarReporteExcel(
            @RequestParam("fechaDesde") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaDesde,
            @RequestParam("fechaHasta") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaHasta) throws IOException, IOException {

        byte[] excelContent = reporteService.generarReporteExcel(fechaDesde, fechaHasta);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_pedidos.xls");
        headers.setContentLength(excelContent.length);

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelContent);
    }
    // http://localhost:8080/reporte/pdf/1
    @GetMapping("/pdf/{id}")
    public ResponseEntity<byte[]> generarPdfInstrumento(@PathVariable Long id) {
        try {
            byte[] pdfBytes = reporteService.generateInstrumentoPdf(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "instrumento_" + id + ".pdf");
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
