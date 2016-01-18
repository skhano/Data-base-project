package db61b;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RowTest {

    @Test
    public void rowTest() {
        String[] colNames = new String[] {"Col1", "Col2", "Col3"};
        Table table1 = new Table("Table1", colNames);
        String[] values = new String[] { "1", "2", "3" };
        Row row1 = new Row(values);
        table1.add(row1);
        String[] values2 = new String[] { "4", "5", "6" };
        Row row2 = new Row(values2);
        table1.add(row2);
        TableIterator it = table1.tableIterator();

        assertEquals("1", it.value(0));
        assertEquals("2", it.value(1));
        assertEquals("3", it.value(2));

        it.next();

        assertEquals("4", it.value(0));
        assertEquals("5", it.value(1));
        assertEquals("6", it.value(2));

    }

}
