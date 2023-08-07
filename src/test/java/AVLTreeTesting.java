import org.example.selfbalancing.AVLTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class AVLTreeTesting {
    public static Stream<Arguments> avlTree() {
        Arguments[] dataset = new Arguments[300];

        for(int i=0;i<dataset.length;i++) {
            int size = (int) (Math.random() * 10_000 + 300);

            Double[] data = new Double[size];

            for(int j=0;j<data.length;j++) {
                data[j] = Math.random() * 10_000 + 10;
            }

            dataset[i] = Arguments.of((Object) data);
        }

        return Stream.of(dataset);
    }

    @ParameterizedTest
    @MethodSource("avlTree")
    public void avlTreeSortingOrder(Double[] arr) {
        AVLTree<Double> tree = new AVLTree<>(arr);

        List<Double> actual = tree.sortedOrder();

        Arrays.sort(arr);

        for(int i=0;i<actual.size();i++) {
            Assertions.assertEquals(arr[i], actual.get(i));
        }
    }
}