# Fastjson

````
    @Value("${sea.fastjson.writeNullValue:false}")
    private Boolean writeNullValueFlag;

    @Bean
    @ConditionalOnClass(FastJsonHttpMessageConverter.class)
    @ConditionalOnProperty(prefix = "sea", name = "fastjson.enable", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean
    public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();


        //支持空值输出
        if (writeNullValueFlag.booleanValue()) {
            fastJsonConfig.setSerializerFeatures(
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.QuoteFieldNames,
                    SerializerFeature.IgnoreErrorGetter,
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.DisableCircularReferenceDetect,
                    SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteNullBooleanAsFalse,
                    SerializerFeature.WriteNullListAsEmpty,
                    SerializerFeature.WriteNullNumberAsZero,
                    SerializerFeature.WriteNullStringAsEmpty
            );
        } else {
            fastJsonConfig.setSerializerFeatures(
                    SerializerFeature.PrettyFormat,
                    SerializerFeature.QuoteFieldNames,
                    SerializerFeature.IgnoreErrorGetter,
                    SerializerFeature.WriteDateUseDateFormat,
                    SerializerFeature.DisableCircularReferenceDetect
            );
        }

        //
        fastConverter.setFastJsonConfig(fastJsonConfig);

        fastConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON,
                MediaType.APPLICATION_JSON_UTF8));

        return fastConverter;
    }
````