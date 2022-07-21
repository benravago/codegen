package bc.util;

/**
 * The murmur hash is a relative fast hash function for platforms with efficient multiplication.
 *
 * Derived from public domain code by Viliam Holub.
 * This is a re-implementation of the original C code from http://murmurhash.googlepages.com/ plus some additional features;
 */
public final class Murmur {

  /**
   * Generates 32 bit hash from byte array of the given length and seed.
   *
   * @param b byte array to hash
   * @param length length of the array to hash
   * @param seed initial seed value
   * @return 32 bit hash of the given array
   */
  public static int hash(final byte[] b, int off, int len, int seed) {
    // 'm' and 'r' are mixing constants generated offline.
    // They're not really 'magic', they just happen to work well.
    final int m = 0x5bd1e995;
    final int r = 24;
    // Initialize the hash to a random value
    int h = seed ^ len;

    int i = off;
    int j = off + (len & ~3);
    while (i < j) {
      int k = (b[i+0]&0xff) + ((b[i+1]&0xff)<<8) + ((b[i+2]&0xff)<<16) + ((b[i+3]&0xff)<<24);
      k *= m;
      k ^= k >>> r;
      k *= m;
      h *= m;
      h ^= k;
      i += 4;
    }

    // Handle the last few bytes of the input array
    switch (len % 4) {
      case 3: h ^= (b[i+2] & 0xff) << 16;
      case 2: h ^= (b[i+1] & 0xff) << 8;
      case 1: h ^= (b[i  ] & 0xff);
              h *= m;
    }

    h ^= h >>> 13;
    h *= m;
    h ^= h >>> 15;

    return h;
  }

  /**
   * Generates 32 bit hash from byte array with default seed value.
   *
   * @param data byte array to hash
   * @return 32 bit hash of the given array
   */
  public static int hash(final byte[] data) {
    return hash( data, 0, data.length, SEED);
  }

  public static final int SEED = 0x9747b28c;

}
