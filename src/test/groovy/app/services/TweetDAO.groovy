package app.services

import app.entities.Tweet
import org.apache.tapestry5.hibernate.annotations.CommitAfter


interface TweetDAO {

    List<Tweet> getAll()

    @CommitAfter
    void deleteAll()

    @CommitAfter
    void addNew(String fromUser, String message)

    @CommitAfter
    void failWithRuntimeException()

    @CommitAfter
    void failWithCheckedException() throws TweetException
}
