package interviewPractice;

public class methods {

    private static int findIndex(int[] arr) {

        if (arr[0] > arr.length) {
            return -1;
        }

        if (arr[0] == 0) {
            return 0;
        }

        int[] arr2 = new int[arr.length - 1];
        for(int i = 1; i<arr.length; i++){
            arr2[i - 1] = arr[i] - 1;
        }
        return 1 + findIndex(arr2);
    }

    public static void main(String[] args) {
        int[] arr = {-10, -5, 0, 3, 7, 9, 12, 15}; // Sorted array with fixed point at index 3
        System.out.println("Fixed point index: " + findIndex(arr));
    }


}
