import com.hyperionml.pojo.Student;
import com.hyperionml.utils.impl.MyLinkedList;
import com.hyperionml.utils.impl.sqList;
import org.junit.Test;

public class ListTest {

    //这是实例数据
    Student hyperionml = new Student("hyperionml", 1, 99, 60, 98, 80);
    Student aaa = new Student("aaaa", 2, 89, 60, 88, 70);
    Student[] students = {hyperionml, aaa};
    sqList<Student> list = new sqList<>(students, 2);
    MyLinkedList<Student> linkedList = new MyLinkedList<>(students);

    public ListTest() throws Throwable {
    }


    @Test
    public void testFind(){
        System.out.println(list.find(aaa));
    }

    @Test
    public void testPrintList(){
        list.printList();
    }

    @Test
    public void testGetElement() throws Throwable {
        Student student = list.getElement(1);
        System.out.println(student);
    }

    @Test
    public void testInsert() throws Throwable {
        Student student = new Student("bbb", 3, 80, 70, 60, 50);
        list.insert(2, student);
        list.printList();
    }

    @Test
    public void testDelete() throws Throwable {
        list.delete(2);
        list.printList();
    }



    @Test
    public void testLinkedFind(){//由于我在实验室录制很安静所以我无法语言讲解
        //这是链表的测试类和方法，和顺序表一样也是5大功能。
        //这是查找aaa这个学生，返回的时索引位置，为1
        System.out.println(linkedList.find(aaa));
    }

    @Test
    public void testLinkedPrintList(){
        //这是输出表
        list.printList();
    }

    @Test
    public void testLinkedGetElement() throws Throwable {
        //这是获取索引为以的元素，这是的1是在链表中的位置而非真正的索引
        Student student = list.getElement(1);
        System.out.println(student);
    }

    @Test
    public void testLinkedInsert() throws Throwable {
        //这是在指定位置插入，index=2，即为索引1的位置
        Student student = new Student("bbb", 3, 80, 70, 60, 50);
        list.insert(2, student);
        list.printList();
    }

    @Test
    public void testLinkedDelete() throws Throwable {
        //这是删除第二个学生，所以只剩一个
        list.delete(2);
        list.printList();
    }
}
