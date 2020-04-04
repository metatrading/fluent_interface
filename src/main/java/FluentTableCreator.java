import java.util.List;
import java.util.function.Function;

public class FluentTableCreator {

    public static void main(String[] args) {
        // enumも駆使すると、エディターぽくなる。
        Function<TableDataBuilder.RowBuilder.CellBasicBuilder, Cell> runtime =
                c -> c.name("column1").type("varchar").build();

        List<Row> list = new TableDataBuilder()
                .row(r -> r.cell(runtime))
                .row(r -> r.cell(c -> c.name("column2").type("varchar")
                    .require().nullrable(true).length(20).build())
                )
                .build();

        list.forEach(row -> {
            row.getCellList().forEach(cell -> {
                System.out.println(cell.toString());
            });
        });
    }
}
