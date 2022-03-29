package dannik.algorithms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dannik.algorithms.services.Difference2SortedArrays;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Named.named;

@DisplayName("Difference 2 sorted arrays tests")
@SpringBootTest
class Difference2SortedArraysTests {

    @Autowired
    private Difference2SortedArrays difference2SortArrays;

    @DisplayName("Should pass with all params")
    @ParameterizedTest(name = "{index} => pair={0}")
    @ArgumentsSource(Difference2SortArraysArgumentProvider.class)
    void success(Pair<int[], int[]> pair) {
        int[] arr1 = pair.getLeft();
        int[] arr2 = pair.getRight();

        var expected = Arrays.stream(ArrayUtils.isEmpty(arr1) ? new int[]{} : arr1)
                .filter(number -> !ArrayUtils.contains(arr2, number))
                .boxed()
                .collect(Collectors.toList());
        var expected2 = Arrays.stream(ArrayUtils.isEmpty(arr2) ? new int[]{} : arr2)
                .filter(number -> !ArrayUtils.contains(arr1, number))
                .boxed()
                .toList();
        expected.addAll(expected2);
        expected = expected.stream()
                .sorted()
                .collect(Collectors.toList());
        var result = difference2SortArrays.get(arr1, arr2);

        assertEquals(StringUtils.join(expected), StringUtils.join(result));
    }

    static class Difference2SortArraysArgumentProvider implements ArgumentsProvider {

        private static final List<Pair<int[], int[]>> parameters = Arrays.asList(
                Pair.of(
                        null,
                        new int[]{}
                ),
                Pair.of(
                        new int[]{},
                        null

                ),
                Pair.of(
                        new int[]{2, 3, 4},
                        new int[]{}
                ),
                Pair.of(
                        new int[]{},
                        new int[]{2, 3, 5}
                ),
                Pair.of(
                        new int[]{2, 3, 4},
                        new int[]{2, 3, 5}
                ),
                Pair.of(
                        new int[]{2, 4, 5, 7, 8, 10, 12, 15},
                        new int[]{5, 6, 8, 11, 12, 14, 15}
                ),
                Pair.of(
                        new int[]{2, 4, 5, 7, 8, 10, 12, 15},
                        new int[]{5, 6, 8, 11, 12, 14, 15, 18, 20}
                )
        );

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
            final ObjectMapper objectMapper = new ObjectMapper();
            return parameters.stream().map(pair -> {
                try {
                    return Arguments.of(
                            named(
                                    pairToString(objectMapper, pair),
                                    pair
                            )
                    );
                } catch (JsonProcessingException e) {
                    throw new IllegalStateException(e);
                }
            });
        }

        private static String pairToString(
                ObjectMapper objectMapper,
                Pair<int[], int[]> pair
        ) throws JsonProcessingException {
            return String.format("%s %s",
                    objectMapper.writeValueAsString(pair.getLeft()),
                    objectMapper.writeValueAsString(pair.getRight())
            );
        }
    }
}
