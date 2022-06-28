import com.example.flatbuffers.*;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.ByteBuffer;

public class UsExecutionDeserializer implements Deserializer<UsStreetExecution> {

    @Override
    public UsStreetExecution deserialize(String topic, byte[] data) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        FbUsStreetExecution fbExecution = FbUsStreetExecution.getRootAsFbUsStreetExecution(byteBuffer);
        UsStreetExecution execution = new UsStreetExecution();
        execution.setFrontOffice(deserializeFrontOffice(fbExecution));
        execution.setSourceSystem(deserializeSourceSystem(fbExecution));
        execution.setOrder(deserializeOrder(fbExecution));
        execution.setExchange(deserializeExchange(fbExecution));
        execution.setExecution(deserializeExecution(fbExecution));
        return execution;
    }

    private FrontOffice deserializeFrontOffice(FbUsStreetExecution fbExecution) {
        FbFrontOffice fbFrontOffice = fbExecution.frontOffice();
        FrontOffice frontOffice = new FrontOffice();
        frontOffice.setExecutionId(fbFrontOffice.executionId());
        frontOffice.setOrderId(fbFrontOffice.orderId());
        return frontOffice;
    }

    private SourceSystem deserializeSourceSystem(FbUsStreetExecution fbExecution) {
        FbSourceSystem fbSourceSystem = fbExecution.sourceSystem();
        SourceSystem sourceSystem = new SourceSystem();
        sourceSystem.setExecutionId(fbSourceSystem.executionId());
        sourceSystem.setOrderId(fbSourceSystem.orderId());
        sourceSystem.setId(fbSourceSystem.id());
        return sourceSystem;
    }

    private Order deserializeOrder(FbUsStreetExecution fbExecution) {
        FbOrder fbOrder = fbExecution.order();
        Order order = new Order();
        order.setSide(Side.values()[fbOrder.side()]);
        order.setOrderType(OrderType.values()[fbOrder.orderType()]);
        order.setTimeInForce(TimeInForce.values()[fbOrder.timeInForce()]);
        order.setCapacity(Capacity.values()[fbOrder.capacity()]);
        order.setInstrumentId(fbOrder.instrumentId());
        return order;
    }

    private Exchange deserializeExchange(FbUsStreetExecution fbExecution) {
        FbExchage fbExchage = fbExecution.exchange();
        Exchange exchange = new Exchange();
        exchange.setName(fbExchage.name());
        exchange.setExecutionId(fbExchage.executionId());
        exchange.setOrderId(fbExchage.orderId());
        return exchange;
    }

    private Execution deserializeExecution(FbUsStreetExecution fbExecution) {
        FbExecution fbExec = fbExecution.execution();
        Execution execution = new Execution();
        execution.setId(fbExec.id());
        execution.setType(ExecutionType.values()[fbExec.type()]);
        execution.setLastQty(fbExec.lastQty());
        execution.setLastPrice(fbExec.lastPrice());
        execution.setTransactTime(fbExec.transactTime());
        execution.setBookId(fbExec.bookId());
        return execution;
    }
}
