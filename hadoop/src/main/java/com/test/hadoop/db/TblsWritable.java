package com.test.hadoop.db;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

/**
 * 实现DBWritable
 * 
 * TblsWritable需要向mysql中写入数据
 */
public class TblsWritable implements Writable, DBWritable {

	String tbl_name;
	int tbl_age;

	public TblsWritable() {
	}

	public TblsWritable(String name, int age) {
		this.tbl_name = name;
		this.tbl_age = age;
	}

	@Override
	public void write(PreparedStatement statement) throws SQLException {
		statement.setString(1, this.tbl_name);
		statement.setInt(2, this.tbl_age);
	}

	@Override
	public void readFields(ResultSet resultSet) throws SQLException {
		this.tbl_name = resultSet.getString(1);
		this.tbl_age = resultSet.getInt(2);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.tbl_name);
		out.writeInt(this.tbl_age);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.tbl_name = in.readUTF();
		this.tbl_age = in.readInt();
	}

	public String toString() {
		return new String(this.tbl_name + " " + this.tbl_age);
	}

}
