/*
 * Copyright © 2019, Java Keyring
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.javakeyring.internal.osx;

import com.sun.jna.Library;
import com.sun.jna.Pointer;

/**
 * OS X Security library.
 */
@SuppressWarnings({"AbbreviationAsWordInName","MethodName"})
interface SecurityLibrary extends Library {

  public static final int ERR_SEC_SUCCESS = 0;
  public static final int ERR_SEC_ITEM_NOT_FOUND = -25300;

  public int // OSStatus
      SecKeychainFindGenericPassword(
      Pointer keychainOrArray, // CFTypeRef
      int serviceNameLength, // UInt32
      byte[] serviceName, // const char*
      int accountNameLength, // UInt32
      byte[] accountName, // const char*
      int[] passwordLength, // UInt32*
      Pointer[] passwordData, // void**
      Pointer[] itemRef); // SecKeychaingItemRef*

  public int  // OSStatus
      SecKeychainAddGenericPassword(
      Pointer keychain, // SecKeychainRef
      int serviceNameLength, // UInt32
      byte[] serviceName, // const char*
      int accountNameLength, // UInt32
      byte[] accountName, // const char*
      int passwordLength, // UInt32
      byte[] passwordData, // const void*
      Pointer itemRef); // SecKeychainItemRef

  public int // OSStatus
      SecKeychainItemModifyContent(
      Pointer itemRef, // SecKeychainItemRef
      Pointer attrList, // const SecKeychainAttributeList*
      int length, // UInt32
      byte[] data); // const void*

  public int // OSStatus
      SecKeychainItemDelete(
      Pointer itemRef); // SecKeychainItemRef

  public Pointer // CFStringRef
      SecCopyErrorMessageString(
      int status, // OSStatus
      Pointer reserved); // void*

  public int // OSStatus
      SecKeychainItemFreeContent(
      Pointer[] attrList, // SecKeychainAttributeList*
      Pointer data); // void*

}
