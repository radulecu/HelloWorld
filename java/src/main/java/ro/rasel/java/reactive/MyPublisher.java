//package ro.rasel.reactove;
//
//import java.util.concurrent.Flow;
//import java.util.concurrent.Flow.Publisher;
//
//public class MyPublisher implements Publisher {
//    private Flow.Subscriber subscriber;
//
//    @Override
//    public void subscribe(Flow.Subscriber subscriber) {
//        this.subscriber=subscriber;
//        subscriber.onSubscribe(new Flow.Subscription() {
//            @Override
//            public void request(long n) {
//
//            }
//
//            @Override
//            public void cancel() {
//
//            }
//        });
//    }
//
//    public void test(){
//        subscriber.
//    }
//}
