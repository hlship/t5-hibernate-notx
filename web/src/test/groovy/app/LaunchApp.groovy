package app

import org.apache.tapestry5.test.Jetty7Runner


class LaunchApp {

    static void main(args) {
        new Jetty7Runner("src/test/webapp", "/", 8080, 8443).start()
    }
}
