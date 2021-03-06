/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.core.ml.action;

import org.elasticsearch.action.Action;
import org.elasticsearch.action.ActionRequestBuilder;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.support.master.AcknowledgedRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.ElasticsearchClient;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.xpack.core.ml.calendars.Calendar;
import org.elasticsearch.xpack.core.ml.utils.ExceptionsHelper;

import java.io.IOException;
import java.util.Objects;

public class DeleteCalendarAction extends Action<DeleteCalendarAction.Request, AcknowledgedResponse,
        DeleteCalendarAction.RequestBuilder> {

    public static final DeleteCalendarAction INSTANCE = new DeleteCalendarAction();
    public static final String NAME = "cluster:admin/xpack/ml/calendars/delete";

    private DeleteCalendarAction() {
        super(NAME);
    }

    @Override
    public RequestBuilder newRequestBuilder(ElasticsearchClient client) {
        return new RequestBuilder(client, this);
    }

    @Override
    public AcknowledgedResponse newResponse() {
        return new AcknowledgedResponse();
    }

    public static class Request extends AcknowledgedRequest<Request> {


        private String calendarId;

        public Request() {
        }

        public Request(String calendarId) {
            this.calendarId = ExceptionsHelper.requireNonNull(calendarId, Calendar.ID.getPreferredName());
        }

        public String getCalendarId() {
            return calendarId;
        }

        @Override
        public ActionRequestValidationException validate() {
            return null;
        }

        @Override
        public void readFrom(StreamInput in) throws IOException {
            super.readFrom(in);
            calendarId = in.readString();
        }

        @Override
        public void writeTo(StreamOutput out) throws IOException {
            super.writeTo(out);
            out.writeString(calendarId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(calendarId);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Request other = (Request) obj;
            return Objects.equals(calendarId, other.calendarId);
        }
    }

    public static class RequestBuilder extends ActionRequestBuilder<Request, AcknowledgedResponse, RequestBuilder> {

        public RequestBuilder(ElasticsearchClient client, DeleteCalendarAction action) {
            super(client, action, new Request());
        }
    }
}
