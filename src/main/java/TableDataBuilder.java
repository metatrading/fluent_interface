import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TableDataBuilder {
    private List<Row> data = new ArrayList<>();

    public TableDataBuilder row(Function<RowBuilder, RowBuilder> rowBuilderImpl) {
        RowBuilder rowBuilder = new RowBuilder(rowBuilderImpl);
        data.add(rowBuilder.row());
        return this;
    }

    public List<Row> build() {
        return this.data;
    }

    public TableDataBuilder beforeCopyRow(Integer copyeRowSize) {
        Row orgRow = data.get(data.size() - 1);

        for (int i = 0; i < copyeRowSize; i++) {
            data.add(orgRow.clone());
        }

        return this;
    }

    static class RowBuilder {
        private Row row = new Row();

        public RowBuilder(Function<RowBuilder, RowBuilder> rowDefiner) {
            row = rowDefiner.apply(this).row();
        }

        RowBuilder cell(Function<CellBasicBuilder, Cell> cellBuilderImpl) {
            row.getCellList().add(new CellBasicBuilder(cellBuilderImpl).build());
            return this;
        }

        protected Row row() {
            return this.row;
        }

        static class CellBasicBuilder {
            private Cell cell = new Cell();
            private NotRequireCellBuilder builder;

            public CellBasicBuilder(Function<CellBasicBuilder, Cell> cellDefiner) {
                cell = cellDefiner.apply(this);
            }

            CellBasicBuilder name(String name) {
                cell.setName(name);
                return this;
            }

            CellBasicBuilder type(String type) {
                cell.setType(type);
                return this;
            }

            NotRequireCellBuilder require() {
                builder = new NotRequireCellBuilder(this.cell);
                return builder;
            }

            public Cell build() {
                if (builder != null) {
                    return builder.build();
                }
                return this.cell;
            }
        }

        static class NotRequireCellBuilder {
            private Cell cell = new Cell();

            public NotRequireCellBuilder(Cell cell) {
                this.cell = cell;
            }

            public NotRequireCellBuilder nullrable(Boolean isNotNull) {
                cell.setNotNull(isNotNull);
                return this;
            }

            public NotRequireCellBuilder length(Integer length) {
                cell.setLength(length);
                return this;
            }

            public Cell build() {
                return this.cell;
            }
        }
    }
}

@Data
class Row implements Cloneable {
    private List<Cell> cellList = new ArrayList<>();

    public Row clone() {
        Row row = null;
        try {
            row = (Row) super.clone();
            List<Cell> list = this.cellList.stream().map(Cell::clone).collect(Collectors.toList());
            row.setCellList(list);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return row;
    }
}

@Data
@ToString
class Cell implements Cloneable {
    private String name;
    private String type;
    private Boolean notNull;
    private Integer length;

    public Cell clone() {
        Cell cell = null;
        try {
            cell = (Cell) super.clone();
            cell.name=this.name;
            cell.type=this.type;
            cell.notNull=this.notNull;
            cell.length=this.length;
            return cell;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cell;
    }
}
