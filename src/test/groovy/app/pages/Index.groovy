package app.pages

import app.entities.Tweet
import org.apache.tapestry5.annotations.Cached
import org.apache.tapestry5.hibernate.annotations.CommitAfter
import org.apache.tapestry5.ioc.annotations.Inject
import org.hibernate.Session


class Index {

    @Inject
    private Session session;

    @Cached
    public List<Tweet> getTweets() {
        session.createCriteria(Tweet).list()
    }

    @CommitAfter
    void onActionFromDeleteAll(){
        getTweets().each {tweet -> session.delete tweet}
    }

    @CommitAfter
    void onActionFromAddNew() {
        session.persist(new Tweet(fromUser: "hlship", message: "Message added at ${new Date()}"))
    }

    @CommitAfter
    void onActionFromCommitFailure() {
        getTweets().each { tweet -> tweet.message += " (commit will fail)" }

        throw new RuntimeException("Forced Commit Failure")
    }
}
