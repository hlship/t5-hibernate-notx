# t5-hibernate-notx

_notx = No Transaction_

Changes how Tapestry's Hibernate transaction support works to only create transactions for @CommitAfter methods.

This addresses a particular concern, where Tapestry's default behavior (always running everything inside a transaction)
can come at incredible cost in certain scenarios.

This library replaces the implementation of the HibernateSessionManager, which is where transactions are normally
started, committed, and rolled back at the end of the request.

It also replaces the support for the @CommitAfter transaction: @CommitAfter now locally 
starts and commits the transaction.

Note: A transaction commits even if a checked exception is thrown by the method.  A runtime exception will result
in a rollback of the transaction.

## Usage

Update your build to search to the repository; this is how you do it for Gradle:


    repositories {

        // All things JBoss/Javassist/Hibernate
        maven {
            name "howardlewisship.com - snapshots"
            url "http://howardlewisship.com/repository/snapshots"
        }
    }

(Once this is stable and released, it will be in `repository/releases`).

Add the correct dependency to your project:

    compile "com.howardlewisship.t5-hibernate-notx:web:0.0.1-SNAPSHOT"

    
If you are building a standalone application, and not a Tapestry 5 application, you can use the `core` (not `web`)
module.

Just having the module on the classpath will let it initialize and override select parts of Tapestry's default 
Hibernate support.

## Version

This library was built specifically against Tapestry 5.3.7; however, the interfaces that are used are unchanged in
Tapestry 5.4, so it should be a simple matter to use it with the latest versions of Tapestry.

It is possible that Tapestry 5.4 may soon add an option to duplicate this behavior.

## TODO

Support for @CommitAfter on service methods, via decoration, has not been addressed and will likely cause failures
if used.

Testing is incomplete, especially for the web tier use of @CommitAfter (on component methods).

## License

Apache Software License 2.0
