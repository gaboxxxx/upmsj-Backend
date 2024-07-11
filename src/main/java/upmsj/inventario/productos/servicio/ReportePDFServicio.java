package upmsj.inventario.productos.servicio;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import upmsj.inventario.productos.modelo.Carrito;
import upmsj.inventario.productos.modelo.ItemCarrito;
import upmsj.inventario.productos.respositorio.CarritoRepositorio;
import upmsj.inventario.productos.respositorio.ItemCarritoRepositorio;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ReportePDFServicio {

    @Autowired
    private CarritoRepositorio carritoRepositorio;

    @Autowired
    private ItemCarritoRepositorio itemCarritoRepositorio;

    public ByteArrayInputStream generarReportePDF(Integer idCarrito) throws DocumentException {
        Carrito carrito = carritoRepositorio.findById(idCarrito).orElseThrow(() -> new IllegalArgumentException("Carrito no encontrado"));
        List<ItemCarrito> items = itemCarritoRepositorio.findByCarrito(carrito);

        // Ajuste de márgenes para desplazar el contenido hacia abajo
        Document document = new Document(PageSize.A4, 36, 36, 100, 36);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter writer = PdfWriter.getInstance(document, out);
        writer.setPageEvent(new HeaderFooterPageEvent());
        document.open();

        Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK);
        Font fontSubTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, BaseColor.BLACK);
        Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);

        // Añadir un poco más de espacio antes del título
        document.add(Chunk.NEWLINE);

        // Título
        Paragraph titulo = new Paragraph("Reporte de Donación", fontTitulo);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);

        document.add(Chunk.NEWLINE);

        // Datos del Carrito
        document.add(new Paragraph("ID del Carrito: " + carrito.getIdCarrito(), fontNormal));
        document.add(new Paragraph("Estado: " + carrito.getEstado(), fontNormal));
        document.add(new Paragraph("Fecha de Creación: " + carrito.getFechaCreacion(), fontNormal));
        document.add(new Paragraph("Fecha de Actualización: " + carrito.getFechaActualizacion(), fontNormal));
        document.add(new Paragraph("Beneficiario: " + (carrito.getBeneficiario() != null ? carrito.getBeneficiario().getNombreBeneficiario() : "N/A"), fontNormal));
        document.add(Chunk.NEWLINE);

        // Título de la Tabla de Items
        Paragraph subTitulo = new Paragraph("Items en el Carrito", fontSubTitulo);
        subTitulo.setAlignment(Element.ALIGN_CENTER);
        document.add(subTitulo);
        document.add(Chunk.NEWLINE);

        // Tabla de Items
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{2, 4, 2, 2});

        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("ID Producto", fontSubTitulo));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Nombre Producto", fontSubTitulo));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Cantidad", fontSubTitulo));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Unidad Medida", fontSubTitulo));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        hcell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        table.addCell(hcell);

        for (ItemCarrito item : items) {
            PdfPCell cell;

            cell = new PdfPCell(new Phrase(item.getProducto().getIdProducto().toString(), fontNormal));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(item.getProducto().getNombreProducto(), fontNormal));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(item.getCantidadItem().toString(), fontNormal));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(item.getProducto().getUnidadMedida(), fontNormal));
            cell.setPaddingLeft(5);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    class HeaderFooterPageEvent extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfPTable header = new PdfPTable(1);
            try {
                header.setWidths(new int[]{24});
                header.setTotalWidth(527);
                header.setLockedWidth(true);
                header.getDefaultCell().setFixedHeight(100); // Aumentar la altura del encabezado
                header.getDefaultCell().setBorder(Rectangle.BOTTOM);
                header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

                PdfPCell cell = new PdfPCell(new Phrase("Unidad Patronato Municipal San José", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.BOTTOM);
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                header.addCell(cell);

                header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }

            PdfPTable footer = new PdfPTable(1);
            try {
                footer.setWidths(new int[]{24});
                footer.setTotalWidth(527);
                footer.setLockedWidth(true);
                footer.getDefaultCell().setFixedHeight(40);
                footer.getDefaultCell().setBorder(Rectangle.TOP);
                footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

                PdfPCell cell = new PdfPCell(new Phrase(String.format("Página %d", writer.getPageNumber()), FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK)));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(Rectangle.TOP);
                cell.setBorderColor(BaseColor.LIGHT_GRAY);
                footer.addCell(cell);

                footer.writeSelectedRows(0, -1, 34, 50, writer.getDirectContent());
            } catch (DocumentException de) {
                throw new ExceptionConverter(de);
            }
        }
    }
}