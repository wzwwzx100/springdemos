package com.test.hadoop.db;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.lib.db.DBWritable;

public class CityRecord implements Writable, DBWritable {
	private int id;
	private String code;
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void readFields(ResultSet set) throws SQLException {
		this.id = set.getInt("id");
		this.code = set.getString("code");
		this.name = set.getString("name");
	}

	@Override
	public void write(PreparedStatement pst) throws SQLException {
		pst.setInt(1, id);
		pst.setString(2, code);
		pst.setString(3, name);
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.id = in.readInt();
		this.code = Text.readString(in);
		this.name = Text.readString(in);
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeInt(this.id);
		Text.writeString(out, this.code);
		Text.writeString(out, this.name);
	}

	@Override
	public String toString() {
		return this.id + " " + this.code + " " + this.name;
	}
}
