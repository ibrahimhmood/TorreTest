package com.ibrahimhmood.torretest.net;

import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class PostEntity extends UrlEncodedFormEntity
{
    protected List<BasicNameValuePair> pairs;
    protected String contentType;
    protected Header header;
    public PostEntity(List<BasicNameValuePair> pairs, String contentType) throws UnsupportedEncodingException
    {
        super(pairs);
        this.pairs = pairs;
        this.contentType = contentType;
        this.header = new Header()
        {
            @Override
            public String getName()
            {
                return "Content-Type";
            }

            @Override
            public String getValue()
            {
                return PostEntity.this.contentType;
            }

            @Override
            public HeaderElement[] getElements() throws ParseException
            {
                return new HeaderElement[0];
            }
        };
    }
    @Override
    public boolean isRepeatable()
    {
        return false;
    }

    @Override
    public boolean isChunked()
    {
        return false;
    }

    @Override
    public long getContentLength()
    {
        return 0;
    }

    @Override
    public Header getContentType()
    {
        return this.header;
    }

    @Override
    public Header getContentEncoding()
    {
        return this.header;
    }

    @Override
    public InputStream getContent() throws IOException, IllegalStateException
    {
        return null;
    }

    @Override
    public void writeTo(OutputStream stream) throws IOException
    {

    }

    @Override
    public boolean isStreaming()
    {
        return false;
    }

    @Override
    public void consumeContent() throws IOException
    {

    }
}
