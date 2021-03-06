package com.hquery.hrpc.core.route;

import com.hquery.hrpc.core.connector.RpcConnector;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author hquery.huang
 * 2019/4/4 13:51:34
 */
@Data
@Accessors(chain = true)
public class RouteClient {

    private RpcConnector connector;

    private int weight;

    private boolean alive;

    public RouteClient() {
        this.alive = false;
    }
}
