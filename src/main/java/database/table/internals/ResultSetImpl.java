package database.table.internals;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.Map;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 7/2/12
 * Time: 1:04 PM
 */
public class ResultSetImpl implements ResultSet {
  TableIterator iterator;
  Row currentRow;

  public ResultSetImpl(TableIterator iterator) {
    this.iterator = iterator;
  }

  public boolean next() throws SQLException {
    if (iterator.hasNext()) {
      currentRow = iterator.next();
      return true;
    }
    return false;
  }

  public void close() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean wasNull() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public String getString(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean getBoolean(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public byte getByte(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public short getShort(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getInt(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public long getLong(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public float getFloat(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public double getDouble(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public BigDecimal getBigDecimal(int i, int i1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public byte[] getBytes(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Date getDate(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Time getTime(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Timestamp getTimestamp(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public InputStream getAsciiStream(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public InputStream getUnicodeStream(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public InputStream getBinaryStream(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public String getString(String s) throws SQLException {
    String ret = (String) currentRow.get(s);
    return ret;
  }

  public boolean getBoolean(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public byte getByte(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public short getShort(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getInt(String s) throws SQLException {
    Integer i = (Integer) currentRow.get(s);
    return i;
  }

  public long getLong(String s) throws SQLException {
    Long l = (Long) currentRow.get(s);
    return l;
  }

  public float getFloat(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public double getDouble(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public BigDecimal getBigDecimal(String s, int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public byte[] getBytes(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Date getDate(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Time getTime(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Timestamp getTimestamp(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public InputStream getAsciiStream(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public InputStream getUnicodeStream(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public InputStream getBinaryStream(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public SQLWarning getWarnings() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void clearWarnings() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public String getCursorName() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public ResultSetMetaData getMetaData() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Object getObject(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Object getObject(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Object getObject(String s, Class c) {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int findColumn(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Reader getCharacterStream(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Reader getCharacterStream(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public BigDecimal getBigDecimal(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public BigDecimal getBigDecimal(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean isBeforeFirst() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean isAfterLast() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean isFirst() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean isLast() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void beforeFirst() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void afterLast() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean first() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean last() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getRow() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean absolute(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean relative(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean previous() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void setFetchDirection(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getFetchDirection() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void setFetchSize(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getFetchSize() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getType() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getConcurrency() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean rowUpdated() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean rowInserted() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean rowDeleted() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNull(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBoolean(int i, boolean b) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateByte(int i, byte b) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateShort(int i, short s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateInt(int i, int i1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateLong(int i, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateFloat(int i, float v) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateDouble(int i, double v) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBigDecimal(int i, BigDecimal bigDecimal) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateString(int i, String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBytes(int i, byte[] bytes) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateDate(int i, Date date) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateTime(int i, Time time) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateTimestamp(int i, Timestamp timestamp) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateAsciiStream(int i, InputStream inputStream, int i1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBinaryStream(int i, InputStream inputStream, int i1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateCharacterStream(int i, Reader reader, int i1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateObject(int i, Object o, int i1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateObject(int i, Object o) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNull(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBoolean(String s, boolean b) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateByte(String s, byte b) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateShort(String s, short i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateInt(String s, int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateLong(String s, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateFloat(String s, float v) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateDouble(String s, double v) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBigDecimal(String s, BigDecimal bigDecimal) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateString(String s, String s1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBytes(String s, byte[] bytes) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateDate(String s, Date date) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateTime(String s, Time time) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateTimestamp(String s, Timestamp timestamp) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateAsciiStream(String s, InputStream inputStream, int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBinaryStream(String s, InputStream inputStream, int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateCharacterStream(String s, Reader reader, int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateObject(String s, Object o, int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateObject(String s, Object o) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void insertRow() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateRow() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void deleteRow() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void refreshRow() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void cancelRowUpdates() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void moveToInsertRow() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void moveToCurrentRow() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Statement getStatement() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Object getObject(int i, Map<String, Class<?>> stringClassMap) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Ref getRef(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Blob getBlob(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Clob getClob(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Array getArray(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Object getObject(String s, Map<String, Class<?>> stringClassMap) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Ref getRef(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Blob getBlob(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Clob getClob(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Array getArray(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Date getDate(int i, Calendar calendar) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Date getDate(String s, Calendar calendar) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Time getTime(int i, Calendar calendar) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Time getTime(String s, Calendar calendar) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Timestamp getTimestamp(int i, Calendar calendar) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Timestamp getTimestamp(String s, Calendar calendar) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public URL getURL(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public URL getURL(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateRef(int i, Ref ref) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateRef(String s, Ref ref) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBlob(int i, Blob blob) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBlob(String s, Blob blob) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateClob(int i, Clob clob) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateClob(String s, Clob clob) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateArray(int i, Array array) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateArray(String s, Array array) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public RowId getRowId(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public RowId getRowId(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateRowId(int i, RowId rowId) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateRowId(String s, RowId rowId) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public int getHoldability() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean isClosed() throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNString(int i, String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNString(String s, String s1) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNClob(int i, NClob nClob) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNClob(String s, NClob nClob) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public NClob getNClob(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public NClob getNClob(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public SQLXML getSQLXML(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public SQLXML getSQLXML(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateSQLXML(int i, SQLXML sqlxml) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateSQLXML(String s, SQLXML sqlxml) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public String getNString(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public String getNString(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Reader getNCharacterStream(int i) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public Reader getNCharacterStream(String s) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNCharacterStream(int i, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNCharacterStream(String s, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateAsciiStream(int i, InputStream inputStream, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBinaryStream(int i, InputStream inputStream, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateCharacterStream(int i, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateAsciiStream(String s, InputStream inputStream, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBinaryStream(String s, InputStream inputStream, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateCharacterStream(String s, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBlob(int i, InputStream inputStream, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBlob(String s, InputStream inputStream, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateClob(int i, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateClob(String s, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNClob(int i, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNClob(String s, Reader reader, long l) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNCharacterStream(int i, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNCharacterStream(String s, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateAsciiStream(int i, InputStream inputStream) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBinaryStream(int i, InputStream inputStream) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateCharacterStream(int i, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateAsciiStream(String s, InputStream inputStream) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBinaryStream(String s, InputStream inputStream) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateCharacterStream(String s, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBlob(int i, InputStream inputStream) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateBlob(String s, InputStream inputStream) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateClob(int i, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateClob(String s, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNClob(int i, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public void updateNClob(String s, Reader reader) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public <T> T unwrap(Class<T> tClass) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }

  public boolean isWrapperFor(Class<?> aClass) throws SQLException {
    throw new RuntimeException("Unimplemented method in ResultSetImpl");
  }
}
