package simpleapi2.io.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationStatus {
    private String OperationResult;
    private String OperationName;
}
