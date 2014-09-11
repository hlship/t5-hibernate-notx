package com.howardlewisship.notx.modules;

/**
 * This module acts as a mix-in to Tapestry's default Hibernate support to remove automatic transactions.
 * Transactions will only be started by the (replaced) CommitAfter annotation worker.
 * The Hibernate session will neither automatically start, nor rollback a transaction
 * ... if will simply be discarded at the end of each
 * request.
 */
public class NoTxModule {
}
