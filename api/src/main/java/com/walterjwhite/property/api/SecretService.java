package com.walterjwhite.property.api;

public interface SecretService {
  /**
   * Encrypt the plainText and encode it into Base64 text.
   *
   * @param plainText the input to encrypt
   * @return cipherText, encrypted and encoded in Base64
   */
  String encrypt(final String plainText);

  /**
   * Decrypt the cipherText and decode it from Base64 text.
   *
   * @param cipherText the input to decrypt
   * @return plainText, decrypted and decoded from Base64
   */
  String decrypt(final String cipherText);
}
