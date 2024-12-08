package com.hyperionml.pojo;


public class MyHashMap {
    private String[] map;
    private int len;
    private int SL;

    public MyHashMap(int len) {
        this.len = len;
        map = new String[len];
    }

    public String[] getMap() {
        return map;
    }

    //这是按照 线性探测法 来处理插入时hashcode的冲突问题
    public void add1(String element){
        int index = getHashCode(element);
        if(map[index] == null){
            map[index] = element;
            SL += 1;
        }else {
            int re = 1;
            while (index + 1 < len && map[(index + 1)%len] != null){
                index++;
                re++;
            }
            map[(index + 1)%len] = element;
            SL += re;
        }
    }

    //这是按照 二次探测法 来处理插入时hashcode的冲突问题
    public void add2(String element){
        int index = getHashCode(element);
        if(map[index] == null){
            map[index] = element;
            SL += 1;
        }else {
            int i = 1;
            int re = 1;
            while (map[index%len] != null){
                index = index + i;
                if(i > 0){
                    i *= -1;
                }else {
                    i = i * -1 + 1;
                }
                re++;
            }
            map[index%len] = element;
            SL += re;
        }
    }

    //这是hash函数，首先吧字符串的每一个字符转为阿斯科码值相加在使用除留余数法计算hashcode
    private int getHashCode(String element){
        int hashcode = 0;
        for (int i = 0; i < element.toCharArray().length; i++) {
            hashcode += element.toCharArray()[i];
        }
        hashcode %= len;
        return hashcode;
    }

    //在插入方法中每一次插入值都会把当前探测的次数记录在总次数中，所以计算平均查找成功的次数直接除于总元素个数就能得到
    public double getASL(){
        return (double)SL / (double)len;
    }
}
