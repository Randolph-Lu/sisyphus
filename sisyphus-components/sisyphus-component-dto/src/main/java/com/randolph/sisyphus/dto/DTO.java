package com.randolph.sisyphus.dto;

import java.io.Serial;
import java.io.Serializable;

/**
 * Data Transfer object, including Command, Query and Response,
 * Command and Query is CQRS concept.
 *
 * @author Frank Zhang
 * @author <a href = "mailto:randolph_lu@163.com">randolph<a/>
 * @since 5.0.0
 */
public abstract class DTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
