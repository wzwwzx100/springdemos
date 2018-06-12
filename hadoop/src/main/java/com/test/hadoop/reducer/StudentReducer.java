package com.test.hadoop.reducer;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import com.test.hadoop.db.TblsWritable;

public class StudentReducer extends Reducer<LongWritable, Text, TblsWritable, TblsWritable> {

    @Override
    protected void reduce(LongWritable key, Iterable<Text> values,Context context) throws IOException, InterruptedException {
        // values只有一个值，因为key没有相同的
        StringBuilder value = new StringBuilder();
        for(Text text : values){
            value.append(text);
        }
        
        String[] studentArr = value.toString().split("\t");
        
        if(StringUtils.isNotBlank(studentArr[0])){
            /*
             * 姓名    年龄（中间以tab分割）
             * 张明明    45
             */
            String name = studentArr[0].trim();
            
            int age = 0;
            try{
                age = Integer.parseInt(studentArr[1].trim());
            }catch(NumberFormatException e){
            	e.printStackTrace();
            }
            
            context.write(new TblsWritable(name, age), null);  
        }
    }

}
