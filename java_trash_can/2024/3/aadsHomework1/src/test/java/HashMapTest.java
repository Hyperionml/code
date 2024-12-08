import com.hyperionml.pojo.MyHashMap;
import org.junit.Test;

import java.util.Arrays;

public class HashMapTest {
    @Test
    public void test() {
        System.out.println((int)'n' + 'i'+ 'h'+ 'a'+ 'o');
        System.out.println("nihao".hashCode());
    }

    @Test
    public void testAdd1() {
        MyHashMap hashMap = new MyHashMap(6);
        hashMap.add1("jrj");//这些都是按照线性探测来插入元素的
        hashMap.add1("hak");
        hashMap.add1("lxy");
        hashMap.add1("yxl");
        hashMap.add1("yy");
        hashMap.add1("fgc");
        System.out.println(Arrays.toString(hashMap.getMap()));//查看表
        System.out.println(hashMap.getASL());//查看平均成功查找次数
    }

    @Test
    public void testAdd2() {
        MyHashMap hashMap = new MyHashMap(6);
        hashMap.add2("jrj");//这些都是按照二次探测来插入元素的
        hashMap.add2("hak");
        hashMap.add2("yy");
        hashMap.add2("fgc");
        hashMap.add2("lxy");
        hashMap.add2("yxl");
        System.out.println(Arrays.toString(hashMap.getMap()));//查看表
        System.out.println(hashMap.getASL());//查看平均成功查找次数
        // 这个值略大是因为我发现前三个插入的元素的hashcode居然刚好都是一样的，导致后面的元素插入都探测了好几次
    }
}
