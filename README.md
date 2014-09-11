= t5-hibernate-notx


_notx = No Transaction_

Changes how Tapestry's Hibernate transaction support works to only create transactions for @CommitAfter methods.

This addresses a particular concern, where Tapestry's default behavior (always running everything inside a transaction)
can come at incredible cost in certain scenarios.

This library replaces the implementation of the HibernateSessionManager, which is where transactions are started and committed
(and rolled back at the end of the request).

It also replaces the support for the @CommitAfter transaction:  @CommitAfter now locally 
starts and commits the transaction.

Note: A transaction commits even if a checked exception is thrown by the method.  A runtime exception will result
in a rollback of the transaction.

= TODO

Support for @CommitAfter on service methods, via decoration, has not been addressed and will likely cause failures
if used.

Although a simple application has been created to test the @CommitAfter annotation, the tests have not been automated.
