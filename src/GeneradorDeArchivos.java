import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GeneradorDeArchivos {
    public void guardarJson(List<String> lista) {
        try {
            String filename = "historial_consultas_" +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            FileWriter fileWriter = new FileWriter(filename);
            for (String resultado : lista) {
                fileWriter.write(resultado);
                fileWriter.write("\n");
            }
            fileWriter.close();
            System.out.println("Historial de consultas guardado en '" + filename + "'");
        } catch (IOException e) {
            System.out.println("Error al guardar el historial de consultas: " + e.getMessage());
        }
    }
}