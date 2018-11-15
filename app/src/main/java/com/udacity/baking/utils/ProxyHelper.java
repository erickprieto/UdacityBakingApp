package com.udacity.baking.utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.udacity.baking.BakingApplication;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>
 * Utility Class to download Webservice REST resources. This class provides KEY values to
 * read from configuration file, that file contains parameters to access to Udacity Baking App API.
 * </p>
 * <p>
 * example of Udacity Baking App API endpoint.
 * <a href="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json">
 *     Udacity Baking App</a>
 * </p>
 *
 * @author Erick Prieto
 * @since 2018
 */
public class ProxyHelper {

    /**
     * Base URL of WebServices.
     */
    private static final String KEY_WEB_SERVICES_URL_BASE = "web_services_url_base";

    /**
     * Time to wait before to launch a Connection TimeOut.
     */
    private static final String KEY_WEB_SERVICES_CONNECT_TIMEOUT = "web_services_timeout";

    /**
     * Time to wait before to launch a Read Response Connection TimeOut.
     */
    private static final String KEY_WEB_SERVICES_READ_TIMEOUT = "web_services_read_timeout";


    /**
     * Base URL of WebServices.
     */
    private static final String WEB_SERVICES_PATH =
            BakingApplication.getAtributoConfiguracion(KEY_WEB_SERVICES_URL_BASE);

    /**
     * Time to wait before to launch a Connection TimeOut.
     */
    private static final Long WEB_SERVICES_CONNECT_TIMEOUT = Long.parseLong(
            BakingApplication.getAtributoConfiguracion(KEY_WEB_SERVICES_CONNECT_TIMEOUT));

    /**
     * Time to wait before to launch a Read Response Connection TimeOut.
     */
    private static final Long WEB_SERVICES_READ_TIMEOUT = Long.parseLong(
            BakingApplication.getAtributoConfiguracion(KEY_WEB_SERVICES_READ_TIMEOUT));


    /**
     * Only provide Utility methods.
     */
    private ProxyHelper(){}

    /**
     * Create a {@link Gson} Converter, to convert from JSON to
     * {@link com.udacity.baking.models}.
     * @return <code>Gson</code> object to convert.
     */
    private static final Gson getConfiguracionGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    /**
     * Provide Retrofit configuration to consume WebService REST endpoint.
     * @param resourceContract Class of Contract of WebService Endpoints.
     * @param <T> Contract of WebService Endpoints.
     * @return <T> WebService Endpoint like a <code>model</code>
     */
    public static final <T> T getProxy(Class <T> resourceContract){
        Gson gson = getConfiguracionGson();

        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(WEB_SERVICES_PATH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client( new OkHttpClient
                        .Builder()
                        .connectTimeout(WEB_SERVICES_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                        .readTimeout( WEB_SERVICES_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                        .build())
                .build();

        return restAdapter.create(resourceContract);
    }

}