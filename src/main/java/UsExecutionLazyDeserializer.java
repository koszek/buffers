import com.example.flatbuffers.FBUsStreetExecution;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;

public class UsExecutionLazyDeserializer implements Deserializer<UsStreetExecution> {

    @Override
    public UsStreetExecution deserialize(String topic, byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        FBUsStreetExecution fbExecution = FBUsStreetExecution.getRootAsFBUsStreetExecution(byteBuffer);
        return new UsStreetExecutionProxy(fbExecution);
    }

    private static class UsStreetExecutionProxy extends UsStreetExecution {

        private FBUsStreetExecution fbExecution;

        private UsStreetExecutionProxy(FBUsStreetExecution fbExecution) {
            this.fbExecution = fbExecution;
        }

        @Override
        public String getExecutionId() {
            return fbExecution.executionId();
        }

        @Override
        public String getOrderId() {
            return fbExecution.orderId();
        }

        @Override
        public String getSourceSystemId() {
            return fbExecution.sourceSystemId();
        }

        @Override
        public Side getSide() {
            return Side.values()[fbExecution.side()];
        }

        @Override
        public OrderType getOrderType() {
            return OrderType.values()[fbExecution.orderType()];
        }

        @Override
        public TimeInForce getTimeInForce() {
            return TimeInForce.values()[fbExecution.timeInForce()];
        }

        @Override
        public Capacity getCapacity() {
            return Capacity.values()[fbExecution.capacity()];
        }

        @Override
        public long getInstrumentId() {
            return fbExecution.instrumentId();
        }

        @Override
        public String getExchangeName() {
            return fbExecution.exchangeName();
        }

        @Override
        public long getInternalId() {
            return fbExecution.internalId();
        }

        @Override
        public ExecutionType getExecutionType() {
            return ExecutionType.values()[fbExecution.executionType()];
        }

        @Override
        public int getLastQty() {
            return fbExecution.lastQty();
        }

        @Override
        public double getLastPrice() {
            return fbExecution.lastPrice();
        }

        @Override
        public long getTransactTime() {
            return fbExecution.transactTime();
        }

        @Override
        public long getBookId() {
            return fbExecution.bookId();
        }

    }
}
