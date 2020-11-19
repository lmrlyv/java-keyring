## Summary ##

<img align="left" width="180" height="180" src="./src/site/resources/javakeyring.png">

java-keyring is a small library which provides a simple java API to store passwords and secrets __insecurely__ in native os keystores.

Currently Mac OS X, Windows and Linux (GNOME) are supported.

## History ##

Initially an abandoned bitbucket repo, but lotsa love has been given to it.
*   Proper windows credential store access.
*   Delete support.
*   Solid testing.
*   Automated builds in all target environements.

Initial repo: [https://bitbucket.org/east301/java-keyring](https://bitbucket.org/east301/java-keyring)

Cloned from: [https://github.com/javakeyring/java-keyring](https://github.com/javakeyring/java-keyring)

## Security Issues ##

CVE-2018-19358 (Vulnerability)

There is a current investigation on the behaviour of the Secret Service API, as other applications can easily read any secret, if the keyring is unlocked (if a user is logged in, then the login/default collection is unlocked). Available D-Bus protection mechanisms (involving the busconfig and policy XML elements) are not used by default. The Secret Service API was never designed with a secure retrival mechanism.

* CVE-2018-19358 Base Score: __[7.8 HIGH]__, CVSS:3.0
* GNOME Keyring Secret Service API Login Credentials Retrieval Vulnerability Base Score: __[5.5 Medium]__, CVSS:3.0

## Public Service Announcement ##

Please keep in mind the above isn't only about gnome/secret service.   Both os-x and window will ask the runtime to allow __java__ to connect to the key ring.  This should be considered a vunlrability, as all java apps will be allowed access.  I personally wouldn't store any credentials in the system keyring, ever, and especially on a system allowing any java app access.

That said, anything I would be comfortable storing in plain text would be fine.   For example, passwords you may be forced to store in ~/.m2/settings.xml, developement databases creds, etc) or any of the things a developer usually has to store in plain text because there is no better option would be fine to store in the keyring.  At least you can look them up in all your tests/apps in a single location if you are consistent with your service/user naming.  Hopefully these dev services are not available to the internet, you vpn in to them, right?  They may have attack vectors as well.  StrongSwan is pretty easy to set up.

Use a real password manager for your real secrets. Something like Keypass, Enpass, 1Password, Bitwarden, etc.  Keep that password manager locked - make sure it's setup to autolock after you login to something with it.  Use a secondary factor if you can with important services, particularly financial, and e-mail, and if you're in to that sort of thing, social sites - like github.com.

## Implementation ##

__Mac OS X__
*   Passwords are stored using [OS X Keychain](https://support.apple.com/guide/keychain-access/welcome/mac) using [Keychain Services](https://developer.apple.com/documentation/security/keychain_services/keychain_items) api via "Legacy Password Storage". 
  
__Linux/Freedesktop__
*   Passwords are stored using [DBus Secret Service](https://specifications.freedesktop.org/secret-service/), you probably used [Seahorse](https://en.wikipedia.org/wiki/Seahorse_(software)).  Connection is made via the excellent [secret-service](https://github.com/swiesend/secret-service) library.

__Windows__
*   Passwords are stored using [Credential Manager](https://support.microsoft.com/en-us/help/4026814/windows-accessing-credential-manager), exceptions will contain [Error Codes](https://docs.microsoft.com/en-us/windows/win32/debug/system-error-codes).   Access is via the [Wincred](https://docs.microsoft.com/en-us/windows/win32/api/wincred/) api.  

## Usage ##

Dirt simple:

```java
    Keyring keyring = Keyring.create();
    keyring.setPassword("domain", "account", "secret");
    String secret = keyring.getPassword("domain", "account");
    keyring.deletePassword("domain", "account");
```

Recommend creating a dummy value if getPassword() fails, so that users know where to go set the value in their applications.

```java
    final Keyring keyring = Keyring.create();
    final String domain = "someDomain";
    final String account = "someAccount";
    try {
      return keyring.getPassword(domain, account);
    } catch ( PasswordAccessException ex ) {
      keyring.setPassword(domain, account, "ChangeMe");
      throw new RuntimeException("Please add the correct credentials to you keystore " 
          + keyring.getKeyringStorageType()
          + ". The credential is stored under '" + account + "@" + domain + "'"
          + "with a password that is currently 'ChangeMe'");
    }
```

## Building ##

```bash
mvn clean install
```

## License ##

Source code of java-keyring is available under a BSD license. 
See the file LICENSE.EAST301 for more details.

## PRs are Welcome ##

Outstanding work:

*   Windows error message conversion.
*   Provide easy binding for Spring / CDI / etc.
*   Support for build tools like Maven/Gradle.
*   Perhaps optional UI requests for passwords (Wincred/secret-service have Apis at least to prompt users).
*   Convert to Kotlin and test in different Kotlin build target (node/jvm/binary).
*   Update the osx binding to use non-legacy apis.

That said, this library is perfectly usable today and tested on all systems. Checkout the badges above!

## Special Thanks ##

java-keyring uses the following library, thanks a lot!
java-keyring package contains copy of compiled JNA library. 
Source code of the library is available at its project page.

*   [Java native access (JNA)](https://github.com/twall/jna)
*   [Secret Service](https://github.com/swiesend/secret-service)
