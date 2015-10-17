package tests;

import catg.CATG;
import database.table.SymbolicTable;
import database.table.commands.SelectCommand;
import database.table.from.From;
import database.table.groupby.DefaultGroupBy;
import database.table.having.HavingTrue;
import database.table.internals.ForeignKey;
import database.table.internals.Row;
import database.table.internals.Table;
import database.table.internals.TableFactory;
import database.table.operations.IdentityOperation;
import database.table.operations.Operations;
import database.table.select.Select;
import database.table.where.Where;

public class ManyColumnsOrRecords {
	public static int l_c1;
	public static final int RECORD_COUNT = 5;
	public static final int COLUMN_COUNT = 20;

	public  static void testme(Table customers, int c1){
		l_c1 = c1;

		Table t = (new SelectCommand(
                new Select() {
                    public String[] selectAs() {
                        return new String[]{"Id"};
                    }

                    public Operations[] select() {
                        return new Operations[]{new IdentityOperation("Id")};
                    }
                },
                new From(new Table[]{customers}),
                new Where() {
                    @Override
                    public boolean where(Row[] rows) {
                    	for(int i = 1;i < COLUMN_COUNT; i++){
        					Integer c = (Integer) rows[0].get("C"+i);
        					if (c == null || c != l_c1) {
                                CATG.assertIfPossible(1,false);
        						return false;
                            }
        				}
                        CATG.assertIfPossible(1,true);
        				return true;
                    }

                },
                new DefaultGroupBy(),
                new HavingTrue(),
                null, false
        )).execute();

		System.out.println("c1="+c1);
		System.out.println(t.size() + " records are selected.");
		if(t.size() >= RECORD_COUNT){
			System.out.println("reach goal!!");
		}
		else{
			System.out.println("not reach goal...");
		}
    }

    public static void main(String[] args){
    	int c1= CATG.readInt(20);

    	String[] names = new String[COLUMN_COUNT];
    	int[] types = new int[COLUMN_COUNT];
    	int[] constraints = new int[COLUMN_COUNT];
    	ForeignKey[] foreignKeys = new ForeignKey[COLUMN_COUNT];

    	names[0]="Id";
    	types[0] = Table.INT;
    	constraints[0] = Table.PRIMARY;
    	foreignKeys[0] = null;

    	for(int i=1;i<COLUMN_COUNT;i++){
    		names[i] = "C"+ i;
        	types[i] = Table.INT;
        	constraints[i] = Table.NONE;
        	foreignKeys[i] = null;
    	}

        Table customers = TableFactory.create("Customers",
        		names,
        		types,
        		constraints,
        		foreignKeys
        		);

        SymbolicTable.insertSymbolicRows(customers, RECORD_COUNT);

        testme(customers, c1);
    }
}
