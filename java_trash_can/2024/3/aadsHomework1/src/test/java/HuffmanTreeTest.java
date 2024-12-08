import com.hyperionml.pojo.MyHuffmanTree;
import org.junit.Test;

public class HuffmanTreeTest {
    @Test
    public void testCreateHuffmanTree() throws Throwable {
        MyHuffmanTree tree = new MyHuffmanTree();
        tree.sum("Hello world");
        tree.createHuffmanTree();
    }

    @Test
    public void testToCode() throws Throwable {
        MyHuffmanTree tree = new MyHuffmanTree();
        tree.sum("Hello world");
        tree.createHuffmanTree();
        System.out.println(tree.toCode("Hello world"));
    }
}
