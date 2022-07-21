package bc.util;

/**
 * Adapted from "On the Goodness of Binary Search"
 * https://www.tbray.org/ongoing/When/200x/2003/03/22/Binary
 */
public interface Binary {

  static int search(int[] array, int target) {
    return search(array,0,array.length,target);
  }

  static int search(int[] array, int offset, int length, int target) {
    int high = length - 1, low = 0;
    while (high - low > 1) {
      var probe = (low + high) >>> 1;
      var key = array[offset+probe];
      if (key < target) {
        low = probe + 1;
      } else {
        if (key > target) {
          high = probe - 1;
        } else {
          return probe; // key found
        }
      }
    }
    return - (low + 1); // key not found
  }

}
