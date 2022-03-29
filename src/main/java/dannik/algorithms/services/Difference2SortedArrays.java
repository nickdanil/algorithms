package dannik.algorithms.services;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class Difference2SortedArrays {

    public List<Integer> get(@Nonnull int[] arr1, @Nonnull int[] arr2) {
        var checkParamResult = checkParams(arr1, arr2);
        var diff = checkParamResult.getRight();
        if(Boolean.FALSE.equals(checkParamResult.getLeft())) return diff;

        int i = 0;
        int j = 0;
        int arr1length = arr1.length - 1;
        int arr2length = arr2.length - 1;
        boolean isArr1End = false;
        boolean isArr2End = false;

        while (i < arr1.length && j < arr2.length) {
            int number1 = arr1[i];
            int number2 = arr2[j];

            if (isArr1End && isArr2End) break;

            if (number1 == number2) {
                isArr1End = i == arr1length;
                if (i != arr1length) i++;
                isArr2End = j == arr2length;
                if (j != arr2length) j++;
            } else if (isArr1End) {
                diff.add(number2);
                isArr2End = j == arr2length;
                if (!isArr2End) j++;
            } else if (isArr2End) {
                diff.add(number1);
                isArr1End = i == arr1length;
                if (!isArr1End) i++;
            } else {
                if (number1 < number2) {
                    diff.add(number1);
                    isArr1End = i == arr1length;
                    if (!isArr1End) i++;
                } else {
                    diff.add(number2);
                    isArr2End = j == arr2length;
                    if (!isArr2End) j++;
                }
            }
        }
        return diff;
    }

    protected @Nonnull Pair<Boolean, List<Integer>> checkParams(@Nonnull int[] arr1, @Nonnull int[] arr2) {
        List<Integer> diff = new ArrayList<>();
        boolean isSuccessCheck = true;
        if (ArrayUtils.isEmpty(arr1) && ArrayUtils.isEmpty(arr2)) {
            isSuccessCheck = false;
        } else if (ArrayUtils.isEmpty(arr1) && ArrayUtils.isNotEmpty(arr2)) {
            isSuccessCheck = false;
            diff = IntStream.of(arr2).boxed().toList();
        } else if (ArrayUtils.isNotEmpty(arr1) && ArrayUtils.isEmpty(arr2)) {
            isSuccessCheck = false;
            diff = IntStream.of(arr1).boxed().toList();
        }
        return Pair.of(isSuccessCheck, diff);
    }
}
