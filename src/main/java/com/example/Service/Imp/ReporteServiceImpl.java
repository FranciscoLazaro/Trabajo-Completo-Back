package com.example.Service.Imp;

import com.example.Entity.Instrumento;
import com.example.Entity.Pedido;
import com.example.Entity.PedidoDetalle;
import com.example.Repository.InstrumentoRepository;
import com.example.Service.PedidoService;
import com.example.Service.ReporteService;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private PedidoService pedidoService;
    @Autowired
    private InstrumentoRepository instrumentoRepository;

    @Override
    public byte[] generarReporteExcel(Date fechaDesde, Date fechaHasta) throws IOException {
        List<Pedido> pedidos = pedidoService.findByFecha(fechaDesde, fechaHasta);

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reporte de Pedidos");

        // Crear encabezado
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Fecha Pedido", "Instrumento", "Marca", "Modelo", "Cantidad", "Precio", "Subtotal"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        // Llenar datos
        int rowNum = 1;
        for (Pedido pedido : pedidos) {
            for (PedidoDetalle detalle : pedido.getPedidosDetalle()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(pedido.getFechaPedido()));
                row.createCell(1).setCellValue(detalle.getInstrumento().getInstrumento());
                row.createCell(2).setCellValue(detalle.getInstrumento().getMarca());
                row.createCell(3).setCellValue(detalle.getInstrumento().getModelo());
                row.createCell(4).setCellValue(detalle.getCantidad());
                row.createCell(5).setCellValue(detalle.getInstrumento().getPrecio());
                row.createCell(6).setCellValue(detalle.getCantidad() * detalle.getInstrumento().getPrecio());
            }
        }

        // Autosize columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        workbook.close();

        return baos.toByteArray();
    }

    @Override
    public byte[] generateInstrumentoPdf(Long instrumentoId) throws IOException {
        // Busca el instrumento por su ID en la base de datos
        Instrumento instrumento = instrumentoRepository.findById(instrumentoId)
                .orElseThrow(() -> new RuntimeException("Instrumento no encontrado"));

        // Crea un nuevo documento PDF y un ByteArrayOutputStream para almacenarlo
        try (PDDocument document = new PDDocument();
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // Añade una página al documento
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Abre un flujo de contenido para escribir en la página
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Agregar imagen del instrumento si está disponible
                if (instrumento.getImagen() != null && !instrumento.getImagen().isEmpty()) {
                    try (InputStream imageStream = new URL(instrumento.getImagen()).openStream()) {
                        PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, imageStream.readAllBytes(), "imagen");
                        contentStream.drawImage(pdImage, 50, 460, 250, 250); // Ajusta la posición y tamaño según sea necesario
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Fondo negro para el título
                contentStream.setNonStrokingColor(0, 0, 0); // Negro
                contentStream.addRect(50, 720, 520, 30);
                contentStream.fill();

                // Escribe el título del documento en blanco y centrado
                contentStream.setNonStrokingColor(255, 255, 255); // Blanco
                addTextCentered(contentStream, "Detalles", PDType1Font.HELVETICA_BOLD, 16, 55, 728, page);

                // Añadir un fondo gris claro para los detalles del instrumento
                contentStream.setNonStrokingColor(220, 220, 220); // Gris claro
                contentStream.addRect(340, 470, 230, 230);
                contentStream.fill();

                // Borrar cualquier color de fondo establecido anteriormente
                contentStream.setNonStrokingColor(0, 0, 0); // Negro

                // Escribe los detalles del instrumento
                addText(contentStream, instrumento.getCantidadVendida() + " vendidos", PDType1Font.HELVETICA_OBLIQUE, 10, 350, 690);
                addText(contentStream, instrumento.getInstrumento(), PDType1Font.HELVETICA_BOLD, 24, 350, 660);
                addText(contentStream, "$ " + String.valueOf(instrumento.getPrecio()), PDType1Font.HELVETICA_BOLD, 20, 350, 630);
                addText(contentStream, "Marca: " + instrumento.getMarca(), PDType1Font.HELVETICA, 12, 350, 600);
                addText(contentStream, "Modelo: " + instrumento.getModelo(), PDType1Font.HELVETICA, 12, 350, 580);
                addText(contentStream, "Costo de Envío:", PDType1Font.HELVETICA_BOLD, 10, 350, 560);
                addText(contentStream, instrumento.getCostoEnvio(), PDType1Font.HELVETICA, 10, 350, 550);
                addText(contentStream, "Descripción: ", PDType1Font.HELVETICA_BOLD, 12, 55, 440);

                // Añadir un borde alrededor de la descripción
                contentStream.setStrokingColor(169, 169, 169); // Gris oscuro
                contentStream.addRect(50, 410, 520, 50);
                contentStream.stroke();

                // Añadir padding a la descripción
                addText(contentStream, instrumento.getDescripcion(), PDType1Font.HELVETICA, 12, 60, 420);

                contentStream.close();
            }

            // Guarda el documento en el flujo de bytes
            document.save(baos);
            // Retorna el contenido del PDF como un array de bytes
            return baos.toByteArray();
        }
    }

    // agregar texto en el contenido del PDF
    private void addText(PDPageContentStream contentStream, String text, PDFont font, float fontSize, float x, float y) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        contentStream.newLineAtOffset(x, y);
        contentStream.showText(text);
        contentStream.endText();
    }

    // agregar texto centrado en el contenido del PDF
    private void addTextCentered(PDPageContentStream contentStream, String text, PDFont font, float fontSize, float x, float y, PDPage page) throws IOException {
        contentStream.beginText();
        contentStream.setFont(font, fontSize);
        float titleWidth = font.getStringWidth(text) / 1000 * fontSize;
        float centeredX = (page.getMediaBox().getWidth() - titleWidth) / 2;
        contentStream.newLineAtOffset(centeredX, y);
        contentStream.showText(text);
        contentStream.endText();
    }
}
