package app.services

import app.entities.Tweet
import org.apache.tapestry5.ioc.annotations.Inject
import org.hibernate.Session


class TweetDAOImpl implements TweetDAO {

    @Inject
    Session session

    @Override
    List<Tweet> getAll() {
        return session.createCriteria(Tweet).list()
    }

    @Override
    void deleteAll() {
        getAll().each { tweet -> session.delete tweet}
    }

    @Override
    void addNew(String fromUser, String message) {
        session.persist(new Tweet(fromUser: fromUser, message: message))

    }

    @Override
    void failWithRuntimeException() {

        addNew("hlship", "This will not be committed")

        throw new IllegalStateException("Exception will rollback session.")

    }

    @Override
    void failWithCheckedException() throws TweetException {

        addNew("hlship", "Added by failWithCheckedException()")

        throw new TweetException()
    }
}
