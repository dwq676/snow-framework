//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jboss.resteasy.resteasy_jaxrs.i18n;

import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.logging.Logger;
import org.jboss.resteasy.resteasy_jaxrs.i18n.Messages;
import org.jboss.resteasy.util.WeightedLanguage;

public class Messages_$bundle implements Serializable, Messages {
    public static final Messages_$bundle INSTANCE = new Messages_$bundle();
    private static final long serialVersionUID = 1L;
    private static final String alwaysMarkedAtIndex0 = "RESTEASY003000: SelfExpandingBufferredInputStream is always marked at index 0.";
    private static final String ambiguousInheritedAnnotations = "RESTEASY003005: Ambiguous inherited JAX-RS annotations applied to method: %s";
    private static final String annotationsParamNull = "RESTEASY003010: annotations param was null";
    private static final String applicationParamNull = "RESTEASY003015: application param was null";
    private static final String badArguments = "RESTEASY003020: Bad arguments passed to %s";
    private static final String badBase64Character = "RESTEASY003025: Bad Base64 input character decimal {0} in array position {1}";
    private static final String base64InputNotProperlyPadded = "RESTEASY003030: Base64 input not properly padded.";
    private static final String base64StringMustHaveFourCharacters = "RESTEASY003035: Base64-encoded string must have at least four characters, but length specified was %s";
    private static final String baseURINotSetForClientProxy = "RESTEASY003040: You have not set a base URI for the client proxy";
    private static final String cacheControlMaxAgeHeader = "RESTEASY003045: CacheControl max-age header does not have a value: %s.";
    private static final String cacheControlSMaxAgeHeader = "RESTEASY003050: CacheControl s-maxage header does not have a value: %s.";
    private static final String cacheControlValueNull = "RESTEASY003055: Cache-Control value is null";
    private static final String callbackWasNull = "RESTEASY003060: Callback was null";
    private static final String cannotConsumeContentType = "RESTEASY003065: Cannot consume content type";
    private static final String cannotDecodeNullSourceArray = "RESTEASY003070: Cannot decode null source array.";
    private static final String cannotHaveLengthOffset = "RESTEASY003075: Cannot have length offset: %s";
    private static final String cannotHaveNegativeOffset = "RESTEASY003080: Cannot have negative offset: %s";
    private static final String cannotHaveOffset = "RESTEASY003085: Cannot have offset of {0} and length of {1} with array of length {2}";
    private static final String cannotInjectAsynchronousResponse = "RESTEASY003090: You cannot inject AsynchronousResponse outside the scope of an HTTP request";
    private static final String cannotInjectIntoForm = "RESTEASY003095: You cannot inject into a form outside the scope of an HTTP request";
    private static final String cannotSendFormParametersAndEntity = "RESTEASY003100: You cannot send both form parameters and an entity body";
    private static final String cannotSerializeNullArray = "RESTEASY003105: Cannot serialize a null array.";
    private static final String cannotSerializeNullObject = "RESTEASY003110: Cannot serialize a null object.";
    private static final String canOnlySetLinkHeaderRelOrTitle = "RESTEASY003115: You can only set one of LinkHeaderParam.rel() and LinkHeaderParam.title() for on {0}.{1}";
    private static final String cantSetMethod = "RESTEASY003120: Can\'t set method after match";
    private static final String cantSetURI = "RESTEASY003125: Can\'t set URI after match";
    private static final String classIsNotRootResource = "RESTEASY003130: Class is not a root resource.  It, or one of its interfaces must be annotated with @Path: %s implements: ";
    private static final String classMustBeAnnotatedWithPath = "RESTEASY003135: Class must be annotated with @Path to invoke path(Class)";
    private static final String clientRequestDoesntSupportClonable = "RESTEASY003140: ClientRequest doesn\'t implement Clonable.  Notify the RESTEasy staff right away.";
    private static final String clientResponseFailureMediaType = "RESTEASY003145: Unable to find a MessageBodyReader of content-type {0} and type {1}";
    private static final String clientResponseFailureStatus = "RESTEASY003150: Error status {0} {1} returned";
    private static final String constructorMappingInvalid = "RESTEASY003155: Constructor arg paramMapping is invalid";
    private static final String controlCharacterInCookieValue = "RESTEASY003160: Control character in cookie value, consider BASE64 encoding your value";
    private static final String cookieHeaderValueNull = "RESTEASY003165: Cookie header value was null";
    private static final String couldNotCreateEntityFactory = "RESTEASY003170: Could not create a default entity type factory of type {0}";
    private static final String couldNotCreateEntityFactoryMessage = "RESTEASY003175: Could not create a default entity type factory of type {0}. {1}";
    private static final String couldNotCreateURI = "RESTEASY003180: Could not create a URI for {0} in {1}.{2}";
    private static final String couldNotFindClassJndi = "RESTEASY003185: Could not find class %s provided to JNDI Component Resource";
    private static final String couldNotFindConstructor = "RESTEASY003190: Could not find constructor for class: %s";
    private static final String couldNotFindGetterForParam = "RESTEASY003195: URITemplateAnnotationResolver could not find a getter for param %s";
    private static final String couldNotFindMessageBodyReader = "RESTEASY003200: Could not find message body reader for type: {0} of content type: {1}";
    private static final String couldNotFindMethod = "RESTEASY003205: Could not find a method for: %s";
    private static final String couldNotFindResourceForFullPath = "RESTEASY003210: Could not find resource for full path: %s";
    private static final String couldNotFindWriterForContentType = "RESTEASY003215: could not find writer for content-type {0} type: {1}";
    private static final String couldNotGetAValue = "RESTEASY003220: URITemplateAnnotationResolver could not get a value for %s";
    private static final String couldNotIntrospectClass = "RESTEASY003225: URITemplateAnnotationResolver could not introspect class %s";
    private static final String couldNotMatchUpLoggerTypeImplementation = "RESTEASY003230: Could not match up an implementation for LoggerType: %s";
    private static final String couldNotProcessMethod = "RESTEASY003235: Could not process method %s";
    private static final String couldNotReadType = "RESTEASY003240: Could not read type {0} for media type {1}";
    private static final String dateInstancesNotSupported = "RESTEASY003245: Date instances are not supported by this class.";
    private static final String dateNull = "RESTEASY003250: date is null";
    private static final String dataToEncodeNull = "RESTEASY003255: Data to encode was null.";
    private static final String dateValueNull = "RESTEASY003260: dateValue is null";
    private static final String destinationArrayCannotStoreThreeBytes = "RESTEASY003265: Destination array with length {0} cannot have offset of {1} and still store three bytes.";
    private static final String destinationArrayNull = "RESTEASY003270: Destination array was null.";
    private static final String emptyFieldInHeader = "RESTEASY003275: Empty field in: %s.";
    private static final String emptyHostName = "RESTEASY003280: empty host name";
    private static final String entityAlreadyRead = "RESTEASY003285: The entity was already read, and it was of type %s";
    private static final String entityNotBackedByInputStream = "RESTEASY003290: Entity is not backed by an input stream";
    private static final String entityNotOfUnderstoodType = "RESTEASY003295: The object you supplied to registerInterceptor is not of an understood type";
    private static final String entityTagValueNull = "RESTEASY003300: value of EntityTag is null";
    private static final String errorInBase64Stream = "RESTEASY003305: Error in Base64 code reading stream.";
    private static final String eTagParamNull = "RESTEASY003310: eTag param null";
    private static final String excededMaximumForwards = "RESTEASY003315: You have exceeded your maximum forwards ResteasyProviderFactory allows.  Last good uri: %s";
    private static final String failedProcessingArguments = "RESTEASY003320: Failed processing arguments of %s";
    private static final String failedToConstruct = "RESTEASY003325: Failed to construct %s";
    private static final String failedToCreateUri = "RESTEASY003330: Failed to create URI: %s";
    private static final String failedToParseCookie = "RESTEASY003335: Failed to parse cookie string \'%s\'";
    private static final String failureParsingMediaType = "RESTEASY003340: Failure parsing MediaType string: %s";
    private static final String fileTooBig = "RESTEASY003345: File is too big for this convenience method (%s bytes).";
    private static final String garbageAfterQuotedString = "RESTEASY003350: Garbage after quoted string: %s";
    private static final String getRequestCannotHaveBody = "RESTEASY003355: A GET request cannot have a body.";
    private static final String hasNoStringConstructor = "RESTEASY003360: %s has no String constructor";
    private static final String illegalHexadecimalCharacter = "RESTEASY003365: Illegal hexadecimal character {0} at index {1}";
    private static final String illegalResponseMediaType = "RESTEASY003370: Illegal response media type: %s";
    private static final String illegalToInjectCookieParam = "RESTEASY003375: It is illegal to inject a @CookieParam into a singleton";
    private static final String illegalToInjectFormParam = "RESTEASY003380: It is illegal to inject a @FormParam into a singleton";
    private static final String illegalToInjectHeaderParam = "RESTEASY003385: It is illegal to inject a @HeaderParam into a singleton";
    private static final String illegalToInjectMatrixParam = "RESTEASY003390: It is illegal to inject a @MatrixParam into a singleton";
    private static final String illegalToInjectMessageBody = "RESTEASY003395: Illegal to inject a message body into a singleton into %s";
    private static final String illegalToInjectNonInterfaceType = "RESTEASY003400: Illegal to inject a non-interface type into a singleton";
    private static final String illegalToInjectPathParam = "RESTEASY003405: It is illegal to inject a @PathParam into a singleton";
    private static final String illegalToInjectQueryParam = "RESTEASY003410: It is illegal to inject a @QueryParam into a singleton";
    private static final String illegalUriTemplate = "RESTEASY003415: Illegal uri template: %s";
    private static final String improperlyPaddedBase64Input = "RESTEASY003420: Improperly padded Base64 input.";
    private static final String incorrectTypeParameterClientExceptionMapper = "RESTEASY003425: Incorrect type parameter. ClientExceptionMapper requires a subclass of java.lang.Throwable as its type parameter.";
    private static final String incorrectTypeParameterExceptionMapper = "RESTEASY003430: Incorrect type parameter. ExceptionMapper requires a subclass of java.lang.Throwable as its type parameter.";
    private static final String inputStreamEmpty = "RESTEASY003435: Input stream was empty, there is no entity";
    private static final String inputStringNull = "RESTEASY003440: Input string was null.";
    private static final String interceptorClassMustBeAnnotated = "RESTEASY003445: Interceptor class must be annotated with @ServerInterceptor and/or @ClientInterceptor";
    private static final String interceptorClassMustBeAnnotatedWithClass = "RESTEASY003450: Interceptor class %s must be annotated with @ServerInterceptor and/or @ClientInterceptor";
    private static final String interceptorNullFromClass = "RESTEASY003455: interceptor null from class: %s";
    private static final String invalidCharacterInBase64Data = "RESTEASY003460: Invalid character in Base64 data.";
    private static final String invalidEscapeCharacterInCookieValue = "RESTEASY003465: Invalid escape character in cookie value.";
    private static final String invalidHost = "RESTEASY003470: invalid host";
    private static final String invalidPort = "RESTEASY003475: Invalid port value";
    private static final String isNotInitialRequest = "RESTEASY003480: %s is not initial request.  Its suspended and retried.  Aborting.";
    private static final String jndiComponentResourceNotSetCorrectly = "RESTEASY003485: JNDI Component Resource variable is not set correctly: jndi;class;true|false comma delimited";
    private static final String keyCouldNotBeParsed = "RESTEASY003490: The %s config in web.xml could not be parsed, accepted values are true,false or 1,0";
    private static final String lastModifiedParamNull = "RESTEASY003495: lastModified param null";
    private static final String localeValueNull = "RESTEASY003500: Locale value is null";
    private static final String malformedMediaType = "RESTEASY003505: Malformed media type: %s";
    private static final String malformedParameter = "RESTEASY003510: Malformed parameter: %s";
    private static final String malformedParameters = "RESTEASY003515: Malformed parameters: %s.";
    private static final String malformedQualityValue = "RESTEASY003520: Malformed quality value.";
    private static final String mapKeyNull = "RESTEASY003525: map key is null";
    private static final String mapValueNull = "RESTEASY003530: map value is null";
    private static final String marshalledEntityMustHaveTypeInfo = "RESTEASY003535: MarshalledEntity must have type information.";
    private static final String mediaTypeQGreaterThan1 = "RESTEASY003540: MediaType q value cannot be greater than 1.0: %s";
    private static final String mediaTypeQMustBeFloat = "RESTEASY003545: MediaType q parameter must be a float: %s";
    private static final String mediaTypeQWeightedLanguageMustBeFloat = "RESTEASY003550: MediaType q parameter must be a float: %s";
    private static final String mediaTypeValueNull = "RESTEASY003555: MediaType value is null";
    private static final String methodNotAnnotatedWithPath = "RESTEASY003560: method is not annotated with @Path";
    private static final String methodNull = "RESTEASY003565: method was null";
    private static final String missingTypeParameter = "RESTEASY003570: Missing type parameter.";
    private static final String mustDefineConsumes = "RESTEASY003575: You must define a @Consumes type on your client method or interface, or supply a default";
    private static final String mustSetLinkHeaderRelOrTitle = "RESTEASY003580: You must set either LinkHeaderParam.rel() or LinkHeaderParam.title() for on {0}.{1}";
    private static final String mustSetEitherPortOrSSLPort = "RESTEASY003585: You must set either the port or ssl port, not both";
    private static final String mustSetPort = "RESTEASY003590: You must set the port or ssl port";
    private static final String mustUseOneHttpMethod = "RESTEASY003595: You must use at least one, but no more than one http method annotation on: %s";
    private static final String nameParameterNull = "RESTEASY003600: name parameter is null";
    private static final String nameParamIsNull = "RESTEASY003605: name param is null";
    private static final String nameParamWasNull = "RESTEASY003610: name param was null";
    private static final String newCookieValueNull = "RESTEASY003615: NewCookie value is null";
    private static final String noContent = "RESTEASY003620: No content";
    private static final String noContentContentLength0 = "RESTEASY003625: No content.  Content-Length is 0";
    private static final String noLongerASupportedContextParam = "RESTEASY003630: %s is no longer a supported context param.  See documentation for more details";
    private static final String noMatchForAcceptHeader = "RESTEASY003635: No match for accept header";
    private static final String noOutputStreamAllowed = "RESTEASY003640: No output stream allowed";
    private static final String noPublicPathAnnotatedMethod = "RESTEASY003645: No public @Path annotated method for {0}.{1}";
    private static final String noResourceMethodFoundForHttpMethod = "RESTEASY003650: No resource method found for %s, return 405 with Allow header";
    private static final String noResourceMethodFoundForOptions = "RESTEASY003655: No resource method found for options, return OK with Allow header";
    private static final String noTypeInformationForEntity = "RESTEASY003660: No type information to extract entity with, use other getEntity() methods";
    private static final String notAllowedToReflectOnMethod = "RESTEASY003665: Not allowed to reflect on method: %s";
    private static final String notEnoughPathParameters = "RESTEASY003670: You did not supply enough values to fill path parameters";
    private static final String notValidInjectableType = "RESTEASY003675: %s is not a valid injectable type for @Suspend";
    private static final String nullSubresource = "RESTEASY003680: Null subresource for path: %s.";
    private static final String nullValue = "RESTEASY003685: null value";
    private static final String numberOfMatchedSegments = "RESTEASY003690: Number of matched segments greater than actual";
    private static final String oddNumberOfCharacters = "RESTEASY003695: Odd page of characters.";
    private static final String originNotAllowed = "RESTEASY003700: Origin not allowed: %s";
    private static final String paramNull = "RESTEASY003705: param was null";
    private static final String passedInValueNull = "RESTEASY003710: A passed in value was null";
    private static final String pathNull = "RESTEASY003715: path was null";
    private static final String pathParameterNotProvided = "RESTEASY003720: path param %s has not been provided by the parameter map";
    private static final String patternNull = "RESTEASY003725: pattern is null";
    private static final String qValueCannotBeGreaterThan1 = "RESTEASY003730: Accept-Language q value cannot be greater than 1.0 %s";
    private static final String quotedStringIsNotClosed = "RESTEASY003735: Quoted string is not closed: %s";
    private static final String relParamNull = "RESTEASY003740: rel param was null";
    private static final String removingHeaderIllegal = "RESTEASY003745: Removing a header is illegal for an HttpServletResponse";
    private static final String requestMediaTypeNotUrlencoded = "RESTEASY003750: Request media type is not application/x-www-form-urlencoded";
    private static final String requestWasAlreadyExecuted = "RESTEASY003755: Request was already executed";
    private static final String resourceNull = "RESTEASY003760: resource was null";
    private static final String responseIsClosed = "RESTEASY003765: Response is closed.";
    private static final String responseIsCommitted = "RESTEASY003770: Response is committed, can\'t handle exception";
    private static final String schemeSpecificPartNull = "RESTEASY003775: schemeSpecificPart was null";
    private static final String segmentNull = "RESTEASY003780: A segment is null";
    private static final String segmentsParameterNull = "RESTEASY003785: segments parameter was null";
    private static final String shouldBeUnreachable = "RESTEASY003790: Should be unreachable";
    private static final String sourceArrayCannotProcessBytes = "RESTEASY003795: Source array with length {0} cannot have offset of {1} and process {2} bytes.";
    private static final String sourceArrayCannotProcessFourBytes = "RESTEASY003800: Source array with length {0} cannot have offset of {1} and still process four bytes.";
    private static final String sourceArrayNull = "RESTEASY003805: Source array was null.";
    private static final String streamWrappedBySignature = "RESTEASY003810: Stream wrapped by Signature, cannot reset the stream without destroying signature";
    private static final String subresourceHasNoJaxRsAnnotations = "RESTEASY003815: Subresource for target class has no jax-rs annotations.: %s";
    private static final String tClassParameterNull = "RESTEASY003820: tClass parameter is null";
    private static final String tailingGarbage = "RESTEASY003825: Tailing garbage: %s";
    private static final String templateParameterNull = "RESTEASY003830: NULL value for template parameter: %s";
    private static final String templateValuesParamNull = "RESTEASY003835: templateValues param null";
    private static final String titleParamNull = "RESTEASY003840: title param was null";
    private static final String twoMethodsSameName = "RESTEASY003845: there are two method named %s";
    private static final String typeParamNull = "RESTEASY003850: type param was null";
    private static final String unableToCreateURI = "RESTEASY003855: Unable to create URI: %s";
    private static final String unableToDecodeQueryString = "RESTEASY003860: Unable to decode query string";
    private static final String unableToDetermineBaseClass = "RESTEASY003865: Unable to determine base class from Type";
    private static final String unableToExtractParameter = "RESTEASY003870: Unable to extract parameter from http request: {0} value is \'{1}\' for {2}";
    private static final String unableToFindConstructor = "RESTEASY003875: Unable to find a constructor that takes a String param or a valueOf() or fromString() method for {0} on {1} for basetype: {2}";
    private static final String unableToFindContextualData = "RESTEASY003880: Unable to find contextual data of type: %s";
    private static final String unableToFindInjectorFactory = "RESTEASY003885: Unable to find InjectorFactory implementation.";
    private static final String unableToFindJaxRsResource = "RESTEASY003890: Unable to find JAX-RS resource associated with path: %s.";
    private static final String unableToFindPublicConstructorForClass = "RESTEASY003895: Unable to find a public constructor for class %s";
    private static final String unableToFindPublicConstructorForProvider = "RESTEASY003900: Unable to find a public constructor for provider class %s";
    private static final String unableToFindTypeArguments = "RESTEASY003905: Unable to find type arguments of %s";
    private static final String unableToInstantiateClientExceptionMapper = "RESTEASY003910: Unable to instantiate ClientExceptionMapper";
    private static final String unableToInstantiateContextObject = "RESTEASY003915: Unable to instantiate context object %s";
    private static final String unableToInstantiateContextResolver = "RESTEASY003920: Unable to instantiate ContextResolver";
    private static final String unableToInstantiateExceptionMapper = "RESTEASY003925: Unable to instantiate ExceptionMapper";
    private static final String unableToInstantiateForm = "RESTEASY003930: Unable to instantiate @Form class. No no-arg constructor.";
    private static final String unableToInstantiateInjectorFactory = "RESTEASY003935: Unable to instantiate InjectorFactory implementation.";
    private static final String unableToInstantiateMessageBodyReader = "RESTEASY003940: Unable to instantiate MessageBodyReader";
    private static final String unableToInstantiateMessageBodyWriter = "RESTEASY003945: Unable to instantiate MessageBodyWriter";
    private static final String unableToParseDate = "RESTEASY003950: Unable to parse the date %s";
    private static final String unableToParseLinkHeaderNoEndToLink = "RESTEASY003955: Unable to parse Link header.  No end to link: %s";
    private static final String unableToParseLinkHeaderNoEndToParameter = "RESTEASY003960: Unable to parse Link header.  No end to parameter: %s";
    private static final String unableToParseLinkHeaderTooManyLinks = "RESTEASY003965: Unable to parse Link header. Too many links in declaration: %s";
    private static final String unableToResolveTypeVariable = "RESTEASY003970: Unable to resolve type variable";
    private static final String unableToUnmarshalResponse = "RESTEASY003975: Unable to unmarshall response for %s";
    private static final String unexpectedNumberSubclass = "RESTEASY003977: Unexpected Number subclass: %s";
    private static final String unknownInterceptorPrecedence = "RESTEASY003980: Unknown interceptor precedence: %s";
    private static final String unknownMediaTypeResponseEntity = "RESTEASY003985: Unknown media type for response entity";
    private static final String unknownPathParam = "RESTEASY003990: Unknown @PathParam: {0} for path: {1}";
    private static final String unknownStateListener = "RESTEASY003995: Unknown state.  You have a Listener messing up what resteasy expects";
    private static final String unsupportedCollectionType = "RESTEASY004000: Unsupported collectionType: %s";
    private static final String unsupportedParameter = "RESTEASY004005: Unsupported parameter: %s";
    private static final String uriNull = "RESTEASY004010: URI was null";
    private static final String uriParamNull = "RESTEASY004015: uri param was null";
    private static final String uriTemplateParameterNull = "RESTEASY004020: uriTemplate parameter is null";
    private static final String uriValueNull = "RESTEASY004025: URI value is null";
    private static final String userIsNotRegistered = "RESTEASY004030: User is not registered: %s";
    private static final String valueNull = "RESTEASY004035: A value was null";
    private static final String valueParamIsNull = "RESTEASY004040: value param is null";
    private static final String valueParamWasNull = "RESTEASY004045: value param was null";
    private static final String valuesParamIsNull = "RESTEASY004050: values param is null";
    private static final String valuesParamWasNull = "RESTEASY004055: values param was null";
    private static final String valuesParameterNull = "RESTEASY004060: values parameter is null";
    private static final String variantListMustNotBeZero = "RESTEASY004065: Variant list must not be zero";
    private static final String wrongPassword = "RESTEASY004070: Wrong password for: %s";

    protected Messages_$bundle() {
    }

    protected Object readResolve() {
        return INSTANCE;
    }

    @Override
    public String alreadyCanceled() {
        return null;
    }

    @Override
    public String alreadyDone() {
        return null;
    }

    @Override
    public String alreadySuspended() {
        return null;
    }

    @Override
    public String cancel() {
        return null;
    }

    @Override
    public String cancellingWith503() {
        return null;
    }

    @Override
    public String onComplete() {
        return null;
    }

    @Override
    public String onTimeout() {
        return null;
    }

    @Override
    public String requestNotSuspended() {
        return null;
    }

    @Override
    public String scheduledTimeout() {
        return null;
    }

    @Override
    public String schedulingTimeout() {
        return null;
    }

    public final String alwaysMarkedAtIndex0() {
        String result = String.format(this.alwaysMarkedAtIndex0$str(), new Object[0]);
        return result;
    }

    protected String alwaysMarkedAtIndex0$str() {
        return "RESTEASY003000: SelfExpandingBufferredInputStream is always marked at index 0.";
    }

    public final String ambiguousInheritedAnnotations(Method method) {
        String result = String.format(this.ambiguousInheritedAnnotations$str(), new Object[]{method});
        return result;
    }

    protected String ambiguousInheritedAnnotations$str() {
        return "RESTEASY003005: Ambiguous inherited JAX-RS annotations applied to method: %s";
    }

    public final String annotationsParamNull() {
        String result = String.format(this.annotationsParamNull$str(), new Object[0]);
        return result;
    }

    protected String annotationsParamNull$str() {
        return "RESTEASY003010: annotations param was null";
    }

    public final String applicationParamNull() {
        String result = String.format(this.applicationParamNull$str(), new Object[0]);
        return result;
    }

    @Override
    public String attemptingToCast(URL from, URL to) {
        return null;
    }

    protected String applicationParamNull$str() {
        return "RESTEASY003015: application param was null";
    }

    public final String badArguments(String methodName) {
        String result = String.format(this.badArguments$str(), new Object[]{methodName});
        return result;
    }

    protected String badArguments$str() {
        return "RESTEASY003020: Bad arguments passed to %s";
    }

    public final String badBase64Character(int c, int pos) {
        String result = MessageFormat.format(this.badBase64Character$str(), new Object[]{Integer.valueOf(c), Integer.valueOf(pos)});
        return result;
    }

    protected String badBase64Character$str() {
        return "RESTEASY003025: Bad Base64 input character decimal {0} in array position {1}";
    }

    public final String base64InputNotProperlyPadded() {
        String result = String.format(this.base64InputNotProperlyPadded$str(), new Object[0]);
        return result;
    }

    protected String base64InputNotProperlyPadded$str() {
        return "RESTEASY003030: Base64 input not properly padded.";
    }

    public final String base64StringMustHaveFourCharacters(int len) {
        String result = String.format(this.base64StringMustHaveFourCharacters$str(), new Object[]{Integer.valueOf(len)});
        return result;
    }

    protected String base64StringMustHaveFourCharacters$str() {
        return "RESTEASY003035: Base64-encoded string must have at least four characters, but length specified was %s";
    }

    public final String baseURINotSetForClientProxy() {
        String result = String.format(this.baseURINotSetForClientProxy$str(), new Object[0]);
        return result;
    }

    protected String baseURINotSetForClientProxy$str() {
        return "RESTEASY003040: You have not set a base URI for the client proxy";
    }

    public final String cacheControlMaxAgeHeader(String value) {
        String result = String.format(this.cacheControlMaxAgeHeader$str(), new Object[]{value});
        return result;
    }

    protected String cacheControlMaxAgeHeader$str() {
        return "RESTEASY003045: CacheControl max-age header does not have a value: %s.";
    }

    public final String cacheControlSMaxAgeHeader(String value) {
        String result = String.format(this.cacheControlSMaxAgeHeader$str(), new Object[]{value});
        return result;
    }

    protected String cacheControlSMaxAgeHeader$str() {
        return "RESTEASY003050: CacheControl s-maxage header does not have a value: %s.";
    }

    public final String cacheControlValueNull() {
        String result = String.format(this.cacheControlValueNull$str(), new Object[0]);
        return result;
    }

    protected String cacheControlValueNull$str() {
        return "RESTEASY003055: Cache-Control value is null";
    }

    public final String callbackWasNull() {
        String result = String.format(this.callbackWasNull$str(), new Object[0]);
        return result;
    }

    protected String callbackWasNull$str() {
        return "RESTEASY003060: Callback was null";
    }

    public final String cannotConsumeContentType() {
        String result = String.format(this.cannotConsumeContentType$str(), new Object[0]);
        return result;
    }

    protected String cannotConsumeContentType$str() {
        return "RESTEASY003065: Cannot consume content type";
    }

    public final String cannotDecodeNullSourceArray() {
        String result = String.format(this.cannotDecodeNullSourceArray$str(), new Object[0]);
        return result;
    }

    protected String cannotDecodeNullSourceArray$str() {
        return "RESTEASY003070: Cannot decode null source array.";
    }

    public final String cannotHaveLengthOffset(int len) {
        String result = String.format(this.cannotHaveLengthOffset$str(), new Object[]{Integer.valueOf(len)});
        return result;
    }

    protected String cannotHaveLengthOffset$str() {
        return "RESTEASY003075: Cannot have length offset: %s";
    }

    public final String cannotHaveNegativeOffset(int off) {
        String result = String.format(this.cannotHaveNegativeOffset$str(), new Object[]{Integer.valueOf(off)});
        return result;
    }

    protected String cannotHaveNegativeOffset$str() {
        return "RESTEASY003080: Cannot have negative offset: %s";
    }

    public final String cannotHaveOffset(int off, int len, int srcLen) {
        String result = MessageFormat.format(this.cannotHaveOffset$str(), new Object[]{Integer.valueOf(off), Integer.valueOf(len), Integer.valueOf(srcLen)});
        return result;
    }

    protected String cannotHaveOffset$str() {
        return "RESTEASY003085: Cannot have offset of {0} and length of {1} with array of length {2}";
    }

    public final String cannotInjectAsynchronousResponse() {
        String result = String.format(this.cannotInjectAsynchronousResponse$str(), new Object[0]);
        return result;
    }

    protected String cannotInjectAsynchronousResponse$str() {
        return "RESTEASY003090: You cannot inject AsynchronousResponse outside the scope of an HTTP request";
    }

    public final String cannotInjectIntoForm() {
        String result = String.format(this.cannotInjectIntoForm$str(), new Object[0]);
        return result;
    }

    protected String cannotInjectIntoForm$str() {
        return "RESTEASY003095: You cannot inject into a form outside the scope of an HTTP request";
    }

    public final String cannotSendFormParametersAndEntity() {
        String result = String.format(this.cannotSendFormParametersAndEntity$str(), new Object[0]);
        return result;
    }

    protected String cannotSendFormParametersAndEntity$str() {
        return "RESTEASY003100: You cannot send both form parameters and an entity body";
    }

    public final String cannotSerializeNullArray() {
        String result = String.format(this.cannotSerializeNullArray$str(), new Object[0]);
        return result;
    }

    protected String cannotSerializeNullArray$str() {
        return "RESTEASY003105: Cannot serialize a null array.";
    }

    public final String cannotSerializeNullObject() {
        String result = String.format(this.cannotSerializeNullObject$str(), new Object[0]);
        return result;
    }

    protected String cannotSerializeNullObject$str() {
        return "RESTEASY003110: Cannot serialize a null object.";
    }

    public final String canOnlySetLinkHeaderRelOrTitle(String className, String methodName) {
        String result = MessageFormat.format(this.canOnlySetLinkHeaderRelOrTitle$str(), new Object[]{className, methodName});
        return result;
    }

    protected String canOnlySetLinkHeaderRelOrTitle$str() {
        return "RESTEASY003115: You can only set one of LinkHeaderParam.rel() and LinkHeaderParam.title() for on {0}.{1}";
    }

    public final String cantSetMethod() {
        String result = String.format(this.cantSetMethod$str(), new Object[0]);
        return result;
    }

    protected String cantSetMethod$str() {
        return "RESTEASY003120: Can\'t set method after match";
    }

    public final String cantSetURI() {
        String result = String.format(this.cantSetURI$str(), new Object[0]);
        return result;
    }

    protected String cantSetURI$str() {
        return "RESTEASY003125: Can\'t set URI after match";
    }

    public final String classIsNotRootResource(String className) {
        String result = String.format(this.classIsNotRootResource$str(), new Object[]{className});
        return result;
    }

    protected String classIsNotRootResource$str() {
        return "RESTEASY003130: Class is not a root resource.  It, or one of its interfaces must be annotated with @Path: %s implements: ";
    }

    public final String classMustBeAnnotatedWithPath() {
        String result = String.format(this.classMustBeAnnotatedWithPath$str(), new Object[0]);
        return result;
    }

    protected String classMustBeAnnotatedWithPath$str() {
        return "RESTEASY003135: Class must be annotated with @Path to invoke path(Class)";
    }

    public final String clientRequestDoesntSupportClonable() {
        String result = String.format(this.clientRequestDoesntSupportClonable$str(), new Object[0]);
        return result;
    }

    protected String clientRequestDoesntSupportClonable$str() {
        return "RESTEASY003140: ClientRequest doesn\'t implement Clonable.  Notify the RESTEasy staff right away.";
    }

    public final String clientResponseFailureMediaType(MediaType mediaType, Type type) {
        String result = MessageFormat.format(this.clientResponseFailureMediaType$str(), new Object[]{mediaType, type});
        return result;
    }

    protected String clientResponseFailureMediaType$str() {
        return "RESTEASY003145: Unable to find a MessageBodyReader of content-type {0} and type {1}";
    }

    public final String clientResponseFailureStatus(int status, Status responseStatus) {
        String result = MessageFormat.format(this.clientResponseFailureStatus$str(), new Object[]{Integer.valueOf(status), responseStatus});
        return result;
    }

    protected String clientResponseFailureStatus$str() {
        return "RESTEASY003150: Error status {0} {1} returned";
    }

    public final String constructorMappingInvalid() {
        String result = String.format(this.constructorMappingInvalid$str(), new Object[0]);
        return result;
    }

    protected String constructorMappingInvalid$str() {
        return "RESTEASY003155: Constructor arg paramMapping is invalid";
    }

    public final String controlCharacterInCookieValue() {
        String result = String.format(this.controlCharacterInCookieValue$str(), new Object[0]);
        return result;
    }

    protected String controlCharacterInCookieValue$str() {
        return "RESTEASY003160: Control character in cookie value, consider BASE64 encoding your value";
    }

    public final String cookieHeaderValueNull() {
        String result = String.format(this.cookieHeaderValueNull$str(), new Object[0]);
        return result;
    }

    protected String cookieHeaderValueNull$str() {
        return "RESTEASY003165: Cookie header value was null";
    }

    public final String couldNotCreateEntityFactory(String className) {
        String result = MessageFormat.format(this.couldNotCreateEntityFactory$str(), new Object[]{className});
        return result;
    }

    protected String couldNotCreateEntityFactory$str() {
        return "RESTEASY003170: Could not create a default entity type factory of type {0}";
    }

    public final String couldNotCreateEntityFactoryMessage(String className, String message) {
        String result = MessageFormat.format(this.couldNotCreateEntityFactoryMessage$str(), new Object[]{className, message});
        return result;
    }

    protected String couldNotCreateEntityFactoryMessage$str() {
        return "RESTEASY003175: Could not create a default entity type factory of type {0}. {1}";
    }

    public final String couldNotCreateURI(String uri, String className, String methodName) {
        String result = MessageFormat.format(this.couldNotCreateURI$str(), new Object[]{uri, className, methodName});
        return result;
    }

    protected String couldNotCreateURI$str() {
        return "RESTEASY003180: Could not create a URI for {0} in {1}.{2}";
    }

    public final String couldNotFindClassJndi(String className) {
        String result = String.format(this.couldNotFindClassJndi$str(), new Object[]{className});
        return result;
    }

    protected String couldNotFindClassJndi$str() {
        return "RESTEASY003185: Could not find class %s provided to JNDI Component Resource";
    }

    public final String couldNotFindConstructor(String className) {
        String result = String.format(this.couldNotFindConstructor$str(), new Object[]{className});
        return result;
    }

    protected String couldNotFindConstructor$str() {
        return "RESTEASY003190: Could not find constructor for class: %s";
    }

    public final String couldNotFindGetterForParam(String param) {
        String result = String.format(this.couldNotFindGetterForParam$str(), new Object[]{param});
        return result;
    }

    protected String couldNotFindGetterForParam$str() {
        return "RESTEASY003195: URITemplateAnnotationResolver could not find a getter for param %s";
    }

    public final String couldNotFindMessageBodyReader(Type type, MediaType mediaType) {
        String result = MessageFormat.format(this.couldNotFindMessageBodyReader$str(), new Object[]{type, mediaType});
        return result;
    }

    protected String couldNotFindMessageBodyReader$str() {
        return "RESTEASY003200: Could not find message body reader for type: {0} of content type: {1}";
    }

    public final String couldNotFindMethod(Method method) {
        String result = String.format(this.couldNotFindMethod$str(), new Object[]{method});
        return result;
    }

    protected String couldNotFindMethod$str() {
        return "RESTEASY003205: Could not find a method for: %s";
    }

    public final String couldNotFindResourceForFullPath(URI uri) {
        String result = String.format(this.couldNotFindResourceForFullPath$str(), new Object[]{uri});
        return result;
    }

    protected String couldNotFindResourceForFullPath$str() {
        return "RESTEASY003210: Could not find resource for full path: %s";
    }

    public final String couldNotFindWriterForContentType(MediaType mediaType, String className) {
        String result = MessageFormat.format(this.couldNotFindWriterForContentType$str(), new Object[]{mediaType, className});
        return result;
    }

    protected String couldNotFindWriterForContentType$str() {
        return "RESTEASY003215: could not find writer for content-type {0} type: {1}";
    }

    public final String couldNotGetAValue(String param) {
        String result = String.format(this.couldNotGetAValue$str(), new Object[]{param});
        return result;
    }

    protected String couldNotGetAValue$str() {
        return "RESTEASY003220: URITemplateAnnotationResolver could not get a value for %s";
    }

    public final String couldNotIntrospectClass(String className) {
        String result = String.format(this.couldNotIntrospectClass$str(), new Object[]{className});
        return result;
    }

    @Override
    public String couldNotMatchUpLoggerTypeImplementation(Class<?> loggerType) {
        return null;
    }

    protected String couldNotIntrospectClass$str() {
        return "RESTEASY003225: URITemplateAnnotationResolver could not introspect class %s";
    }

    public final String couldNotMatchUpLoggerTypeImplementation(Logger.LoggerType loggerType) {
        String result = String.format(this.couldNotMatchUpLoggerTypeImplementation$str(), new Object[]{loggerType});
        return result;
    }

    protected String couldNotMatchUpLoggerTypeImplementation$str() {
        return "RESTEASY003230: Could not match up an implementation for LoggerType: %s";
    }

    public final String couldNotProcessMethod(Method method) {
        String result = String.format(this.couldNotProcessMethod$str(), new Object[]{method});
        return result;
    }

    protected String couldNotProcessMethod$str() {
        return "RESTEASY003235: Could not process method %s";
    }

    public final String couldNotReadType(Type type, MediaType mediaType) {
        String result = MessageFormat.format(this.couldNotReadType$str(), new Object[]{type, mediaType});
        return result;
    }

    protected String couldNotReadType$str() {
        return "RESTEASY003240: Could not read type {0} for media type {1}";
    }

    public final String dateInstancesNotSupported() {
        String result = String.format(this.dateInstancesNotSupported$str(), new Object[0]);
        return result;
    }

    protected String dateInstancesNotSupported$str() {
        return "RESTEASY003245: Date instances are not supported by this class.";
    }

    public final String dateNull() {
        String result = String.format(this.dateNull$str(), new Object[0]);
        return result;
    }

    protected String dateNull$str() {
        return "RESTEASY003250: date is null";
    }

    public final String dataToEncodeNull() {
        String result = String.format(this.dataToEncodeNull$str(), new Object[0]);
        return result;
    }

    protected String dataToEncodeNull$str() {
        return "RESTEASY003255: Data to encode was null.";
    }

    public final String dateValueNull() {
        String result = String.format(this.dateValueNull$str(), new Object[0]);
        return result;
    }

    protected String dateValueNull$str() {
        return "RESTEASY003260: dateValue is null";
    }

    public final String destinationArrayCannotStoreThreeBytes(int len, int off) {
        String result = MessageFormat.format(this.destinationArrayCannotStoreThreeBytes$str(), new Object[]{Integer.valueOf(len), Integer.valueOf(off)});
        return result;
    }

    protected String destinationArrayCannotStoreThreeBytes$str() {
        return "RESTEASY003265: Destination array with length {0} cannot have offset of {1} and still store three bytes.";
    }

    public final String destinationArrayNull() {
        String result = String.format(this.destinationArrayNull$str(), new Object[0]);
        return result;
    }

    protected String destinationArrayNull$str() {
        return "RESTEASY003270: Destination array was null.";
    }

    public final String emptyFieldInHeader(String header) {
        String result = String.format(this.emptyFieldInHeader$str(), new Object[]{header});
        return result;
    }

    protected String emptyFieldInHeader$str() {
        return "RESTEASY003275: Empty field in: %s.";
    }

    public final String emptyHostName() {
        String result = String.format(this.emptyHostName$str(), new Object[0]);
        return result;
    }

    protected String emptyHostName$str() {
        return "RESTEASY003280: empty host name";
    }

    public final String entityAlreadyRead(Class clazz) {
        String result = String.format(this.entityAlreadyRead$str(), new Object[]{clazz});
        return result;
    }

    protected String entityAlreadyRead$str() {
        return "RESTEASY003285: The entity was already read, and it was of type %s";
    }

    public final String entityNotBackedByInputStream() {
        String result = String.format(this.entityNotBackedByInputStream$str(), new Object[0]);
        return result;
    }

    protected String entityNotBackedByInputStream$str() {
        return "RESTEASY003290: Entity is not backed by an input stream";
    }

    public final String entityNotOfUnderstoodType() {
        String result = String.format(this.entityNotOfUnderstoodType$str(), new Object[0]);
        return result;
    }

    protected String entityNotOfUnderstoodType$str() {
        return "RESTEASY003295: The object you supplied to registerInterceptor is not of an understood type";
    }

    public final String entityTagValueNull() {
        String result = String.format(this.entityTagValueNull$str(), new Object[0]);
        return result;
    }

    protected String entityTagValueNull$str() {
        return "RESTEASY003300: value of EntityTag is null";
    }

    public final String errorInBase64Stream() {
        String result = String.format(this.errorInBase64Stream$str(), new Object[0]);
        return result;
    }

    protected String errorInBase64Stream$str() {
        return "RESTEASY003305: Error in Base64 code reading stream.";
    }

    public final String eTagParamNull() {
        String result = String.format(this.eTagParamNull$str(), new Object[0]);
        return result;
    }

    protected String eTagParamNull$str() {
        return "RESTEASY003310: eTag param null";
    }

    public final String excededMaximumForwards(String uri) {
        String result = String.format(this.excededMaximumForwards$str(), new Object[]{uri});
        return result;
    }

    protected String excededMaximumForwards$str() {
        return "RESTEASY003315: You have exceeded your maximum forwards ResteasyProviderFactory allows.  Last good uri: %s";
    }

    public final String failedProcessingArguments(String constructor) {
        String result = String.format(this.failedProcessingArguments$str(), new Object[]{constructor});
        return result;
    }

    protected String failedProcessingArguments$str() {
        return "RESTEASY003320: Failed processing arguments of %s";
    }

    public final String failedToConstruct(String constructor) {
        String result = String.format(this.failedToConstruct$str(), new Object[]{constructor});
        return result;
    }

    protected String failedToConstruct$str() {
        return "RESTEASY003325: Failed to construct %s";
    }

    public final String failedToCreateUri(String buf) {
        String result = String.format(this.failedToCreateUri$str(), new Object[]{buf});
        return result;
    }

    protected String failedToCreateUri$str() {
        return "RESTEASY003330: Failed to create URI: %s";
    }

    public final String failedToParseCookie(String value) {
        String result = String.format(this.failedToParseCookie$str(), new Object[]{value});
        return result;
    }

    protected String failedToParseCookie$str() {
        return "RESTEASY003335: Failed to parse cookie string \'%s\'";
    }

    public final String failureParsingMediaType(String type) {
        String result = String.format(this.failureParsingMediaType$str(), new Object[]{type});
        return result;
    }

    protected String failureParsingMediaType$str() {
        return "RESTEASY003340: Failure parsing MediaType string: %s";
    }

    public final String fileTooBig(long len) {
        String result = String.format(this.fileTooBig$str(), new Object[]{Long.valueOf(len)});
        return result;
    }

    protected String fileTooBig$str() {
        return "RESTEASY003345: File is too big for this convenience method (%s bytes).";
    }

    public final String garbageAfterQuotedString(String header) {
        String result = String.format(this.garbageAfterQuotedString$str(), new Object[]{header});
        return result;
    }

    protected String garbageAfterQuotedString$str() {
        return "RESTEASY003350: Garbage after quoted string: %s";
    }

    public final String getRequestCannotHaveBody() {
        String result = String.format(this.getRequestCannotHaveBody$str(), new Object[0]);
        return result;
    }

    @Override
    public String gzipExceedsMaxSize(int size) {
        return null;
    }

    protected String getRequestCannotHaveBody$str() {
        return "RESTEASY003355: A GET request cannot have a body.";
    }

    public final String hasNoStringConstructor(String className) {
        String result = String.format(this.hasNoStringConstructor$str(), new Object[]{className});
        return result;
    }

    protected String hasNoStringConstructor$str() {
        return "RESTEASY003360: %s has no String constructor";
    }

    public final String illegalHexadecimalCharacter(char ch, int index) {
        String result = MessageFormat.format(this.illegalHexadecimalCharacter$str(), new Object[]{Character.valueOf(ch), Integer.valueOf(index)});
        return result;
    }

    protected String illegalHexadecimalCharacter$str() {
        return "RESTEASY003365: Illegal hexadecimal character {0} at index {1}";
    }

    public final String illegalResponseMediaType(String mediaType) {
        String result = String.format(this.illegalResponseMediaType$str(), new Object[]{mediaType});
        return result;
    }

    protected String illegalResponseMediaType$str() {
        return "RESTEASY003370: Illegal response media type: %s";
    }

    public final String illegalToInjectCookieParam() {
        String result = String.format(this.illegalToInjectCookieParam$str(), new Object[0]);
        return result;
    }

    protected String illegalToInjectCookieParam$str() {
        return "RESTEASY003375: It is illegal to inject a @CookieParam into a singleton";
    }

    public final String illegalToInjectFormParam() {
        String result = String.format(this.illegalToInjectFormParam$str(), new Object[0]);
        return result;
    }

    protected String illegalToInjectFormParam$str() {
        return "RESTEASY003380: It is illegal to inject a @FormParam into a singleton";
    }

    public final String illegalToInjectHeaderParam() {
        String result = String.format(this.illegalToInjectHeaderParam$str(), new Object[0]);
        return result;
    }

    protected String illegalToInjectHeaderParam$str() {
        return "RESTEASY003385: It is illegal to inject a @HeaderParam into a singleton";
    }

    public final String illegalToInjectMatrixParam() {
        String result = String.format(this.illegalToInjectMatrixParam$str(), new Object[0]);
        return result;
    }

    protected String illegalToInjectMatrixParam$str() {
        return "RESTEASY003390: It is illegal to inject a @MatrixParam into a singleton";
    }

    public final String illegalToInjectMessageBody(AccessibleObject target) {
        String result = String.format(this.illegalToInjectMessageBody$str(), new Object[]{target});
        return result;
    }

    protected String illegalToInjectMessageBody$str() {
        return "RESTEASY003395: Illegal to inject a message body into a singleton into %s";
    }

    public final String illegalToInjectNonInterfaceType() {
        String result = String.format(this.illegalToInjectNonInterfaceType$str(), new Object[0]);
        return result;
    }

    protected String illegalToInjectNonInterfaceType$str() {
        return "RESTEASY003400: Illegal to inject a non-interface type into a singleton";
    }

    public final String illegalToInjectPathParam() {
        String result = String.format(this.illegalToInjectPathParam$str(), new Object[0]);
        return result;
    }

    protected String illegalToInjectPathParam$str() {
        return "RESTEASY003405: It is illegal to inject a @PathParam into a singleton";
    }

    public final String illegalToInjectQueryParam() {
        String result = String.format(this.illegalToInjectQueryParam$str(), new Object[0]);
        return result;
    }

    protected String illegalToInjectQueryParam$str() {
        return "RESTEASY003410: It is illegal to inject a @QueryParam into a singleton";
    }

    public final String illegalUriTemplate(String template) {
        String result = String.format(this.illegalUriTemplate$str(), new Object[]{template});
        return result;
    }

    protected String illegalUriTemplate$str() {
        return "RESTEASY003415: Illegal uri template: %s";
    }

    public final String improperlyPaddedBase64Input() {
        String result = String.format(this.improperlyPaddedBase64Input$str(), new Object[0]);
        return result;
    }

    protected String improperlyPaddedBase64Input$str() {
        return "RESTEASY003420: Improperly padded Base64 input.";
    }

    public final String incorrectTypeParameterClientExceptionMapper() {
        String result = String.format(this.incorrectTypeParameterClientExceptionMapper$str(), new Object[0]);
        return result;
    }

    protected String incorrectTypeParameterClientExceptionMapper$str() {
        return "RESTEASY003425: Incorrect type parameter. ClientExceptionMapper requires a subclass of java.lang.Throwable as its type parameter.";
    }

    public final String incorrectTypeParameterExceptionMapper() {
        String result = String.format(this.incorrectTypeParameterExceptionMapper$str(), new Object[0]);
        return result;
    }

    protected String incorrectTypeParameterExceptionMapper$str() {
        return "RESTEASY003430: Incorrect type parameter. ExceptionMapper requires a subclass of java.lang.Throwable as its type parameter.";
    }

    public final String inputStreamEmpty() {
        String result = String.format(this.inputStreamEmpty$str(), new Object[0]);
        return result;
    }

    protected String inputStreamEmpty$str() {
        return "RESTEASY003435: Input stream was empty, there is no entity";
    }

    public final String inputStringNull() {
        String result = String.format(this.inputStringNull$str(), new Object[0]);
        return result;
    }

    protected String inputStringNull$str() {
        return "RESTEASY003440: Input string was null.";
    }

    public final String interceptorClassMustBeAnnotated() {
        String result = String.format(this.interceptorClassMustBeAnnotated$str(), new Object[0]);
        return result;
    }

    protected String interceptorClassMustBeAnnotated$str() {
        return "RESTEASY003445: Interceptor class must be annotated with @ServerInterceptor and/or @ClientInterceptor";
    }

    public final String interceptorClassMustBeAnnotatedWithClass(Class clazz) {
        String result = String.format(this.interceptorClassMustBeAnnotatedWithClass$str(), new Object[]{clazz});
        return result;
    }

    protected String interceptorClassMustBeAnnotatedWithClass$str() {
        return "RESTEASY003450: Interceptor class %s must be annotated with @ServerInterceptor and/or @ClientInterceptor";
    }

    public final String interceptorNullFromClass(String className) {
        String result = String.format(this.interceptorNullFromClass$str(), new Object[]{className});
        return result;
    }

    protected String interceptorNullFromClass$str() {
        return "RESTEASY003455: interceptor null from class: %s";
    }

    public final String invalidCharacterInBase64Data() {
        String result = String.format(this.invalidCharacterInBase64Data$str(), new Object[0]);
        return result;
    }

    protected String invalidCharacterInBase64Data$str() {
        return "RESTEASY003460: Invalid character in Base64 data.";
    }

    public final String invalidEscapeCharacterInCookieValue() {
        String result = String.format(this.invalidEscapeCharacterInCookieValue$str(), new Object[0]);
        return result;
    }

    protected String invalidEscapeCharacterInCookieValue$str() {
        return "RESTEASY003465: Invalid escape character in cookie value.";
    }

    public final String invalidHost() {
        String result = String.format(this.invalidHost$str(), new Object[0]);
        return result;
    }

    protected String invalidHost$str() {
        return "RESTEASY003470: invalid host";
    }

    public final String invalidPort() {
        String result = String.format(this.invalidPort$str(), new Object[0]);
        return result;
    }

    protected String invalidPort$str() {
        return "RESTEASY003475: Invalid port value";
    }

    public final String isNotInitialRequest(String path) {
        String result = String.format(this.isNotInitialRequest$str(), new Object[]{path});
        return result;
    }

    protected String isNotInitialRequest$str() {
        return "RESTEASY003480: %s is not initial request.  Its suspended and retried.  Aborting.";
    }

    public final String jndiComponentResourceNotSetCorrectly() {
        String result = String.format(this.jndiComponentResourceNotSetCorrectly$str(), new Object[0]);
        return result;
    }

    protected String jndiComponentResourceNotSetCorrectly$str() {
        return "RESTEASY003485: JNDI Component Resource variable is not set correctly: jndi;class;true|false comma delimited";
    }

    public final String keyCouldNotBeParsed(String key) {
        String result = String.format(this.keyCouldNotBeParsed$str(), new Object[]{key});
        return result;
    }

    protected String keyCouldNotBeParsed$str() {
        return "RESTEASY003490: The %s config in web.xml could not be parsed, accepted values are true,false or 1,0";
    }

    public final String lastModifiedParamNull() {
        String result = String.format(this.lastModifiedParamNull$str(), new Object[0]);
        return result;
    }

    protected String lastModifiedParamNull$str() {
        return "RESTEASY003495: lastModified param null";
    }

    public final String localeValueNull() {
        String result = String.format(this.localeValueNull$str(), new Object[0]);
        return result;
    }

    protected String localeValueNull$str() {
        return "RESTEASY003500: Locale value is null";
    }

    public final String malformedMediaType(String header) {
        String result = String.format(this.malformedMediaType$str(), new Object[]{header});
        return result;
    }

    protected String malformedMediaType$str() {
        return "RESTEASY003505: Malformed media type: %s";
    }

    public final String malformedParameter(String parameter) {
        String result = String.format(this.malformedParameter$str(), new Object[]{parameter});
        return result;
    }

    protected String malformedParameter$str() {
        return "RESTEASY003510: Malformed parameter: %s";
    }

    public final String malformedParameters(String header) {
        String result = String.format(this.malformedParameters$str(), new Object[]{header});
        return result;
    }

    protected String malformedParameters$str() {
        return "RESTEASY003515: Malformed parameters: %s.";
    }

    public final String malformedQualityValue() {
        String result = String.format(this.malformedQualityValue$str(), new Object[0]);
        return result;
    }

    protected String malformedQualityValue$str() {
        return "RESTEASY003520: Malformed quality value.";
    }

    public final String mapKeyNull() {
        String result = String.format(this.mapKeyNull$str(), new Object[0]);
        return result;
    }

    protected String mapKeyNull$str() {
        return "RESTEASY003525: map key is null";
    }

    public final String mapValueNull() {
        String result = String.format(this.mapValueNull$str(), new Object[0]);
        return result;
    }

    protected String mapValueNull$str() {
        return "RESTEASY003530: map value is null";
    }

    public final String marshalledEntityMustHaveTypeInfo() {
        String result = String.format(this.marshalledEntityMustHaveTypeInfo$str(), new Object[0]);
        return result;
    }

    protected String marshalledEntityMustHaveTypeInfo$str() {
        return "RESTEASY003535: MarshalledEntity must have type information.";
    }

    public final String mediaTypeQGreaterThan1(String mediaType) {
        String result = String.format(this.mediaTypeQGreaterThan1$str(), new Object[]{mediaType});
        return result;
    }

    protected String mediaTypeQGreaterThan1$str() {
        return "RESTEASY003540: MediaType q value cannot be greater than 1.0: %s";
    }

    public final String mediaTypeQMustBeFloat(MediaType mediaType) {
        String result = String.format(this.mediaTypeQMustBeFloat$str(), new Object[]{mediaType});
        return result;
    }

    protected String mediaTypeQMustBeFloat$str() {
        return "RESTEASY003545: MediaType q parameter must be a float: %s";
    }

    public final String mediaTypeQWeightedLanguageMustBeFloat(WeightedLanguage lang) {
        String result = String.format(this.mediaTypeQWeightedLanguageMustBeFloat$str(), new Object[]{lang});
        return result;
    }

    protected String mediaTypeQWeightedLanguageMustBeFloat$str() {
        return "RESTEASY003550: MediaType q parameter must be a float: %s";
    }

    public final String mediaTypeValueNull() {
        String result = String.format(this.mediaTypeValueNull$str(), new Object[0]);
        return result;
    }

    protected String mediaTypeValueNull$str() {
        return "RESTEASY003555: MediaType value is null";
    }

    public final String methodNotAnnotatedWithPath() {
        String result = String.format(this.methodNotAnnotatedWithPath$str(), new Object[0]);
        return result;
    }

    protected String methodNotAnnotatedWithPath$str() {
        return "RESTEASY003560: method is not annotated with @Path";
    }

    public final String methodNull() {
        String result = String.format(this.methodNull$str(), new Object[0]);
        return result;
    }

    protected String methodNull$str() {
        return "RESTEASY003565: method was null";
    }

    public final String missingTypeParameter() {
        String result = String.format(this.missingTypeParameter$str(), new Object[0]);
        return result;
    }

    protected String missingTypeParameter$str() {
        return "RESTEASY003570: Missing type parameter.";
    }

    public final String mustDefineConsumes() {
        String result = String.format(this.mustDefineConsumes$str(), new Object[0]);
        return result;
    }

    protected String mustDefineConsumes$str() {
        return "RESTEASY003575: You must define a @Consumes type on your client method or interface, or supply a default";
    }

    public final String mustSetLinkHeaderRelOrTitle(String className, String methodName) {
        String result = MessageFormat.format(this.mustSetLinkHeaderRelOrTitle$str(), new Object[]{className, methodName});
        return result;
    }

    protected String mustSetLinkHeaderRelOrTitle$str() {
        return "RESTEASY003580: You must set either LinkHeaderParam.rel() or LinkHeaderParam.title() for on {0}.{1}";
    }

    public final String mustSetEitherPortOrSSLPort() {
        String result = String.format(this.mustSetEitherPortOrSSLPort$str(), new Object[0]);
        return result;
    }

    protected String mustSetEitherPortOrSSLPort$str() {
        return "RESTEASY003585: You must set either the port or ssl port, not both";
    }

    public final String mustSetPort() {
        String result = String.format(this.mustSetPort$str(), new Object[0]);
        return result;
    }

    protected String mustSetPort$str() {
        return "RESTEASY003590: You must set the port or ssl port";
    }

    public final String mustUseOneHttpMethod(String methodName) {
        String result = String.format(this.mustUseOneHttpMethod$str(), new Object[]{methodName});
        return result;
    }

    protected String mustUseOneHttpMethod$str() {
        return "RESTEASY003595: You must use at least one, but no more than one http method annotation on: %s";
    }

    public final String nameParameterNull() {
        String result = String.format(this.nameParameterNull$str(), new Object[0]);
        return result;
    }

    protected String nameParameterNull$str() {
        return "RESTEASY003600: name parameter is null";
    }

    public final String nameParamIsNull() {
        String result = String.format(this.nameParamIsNull$str(), new Object[0]);
        return result;
    }

    protected String nameParamIsNull$str() {
        return "RESTEASY003605: name param is null";
    }

    public final String nameParamWasNull() {
        String result = String.format(this.nameParamWasNull$str(), new Object[0]);
        return result;
    }

    protected String nameParamWasNull$str() {
        return "RESTEASY003610: name param was null";
    }

    public final String newCookieValueNull() {
        String result = String.format(this.newCookieValueNull$str(), new Object[0]);
        return result;
    }

    protected String newCookieValueNull$str() {
        return "RESTEASY003615: NewCookie value is null";
    }

    public final String noContent() {
        String result = String.format(this.noContent$str(), new Object[0]);
        return result;
    }

    protected String noContent$str() {
        return "RESTEASY003620: No content";
    }

    public final String noContentContentLength0() {
        String result = String.format(this.noContentContentLength0$str(), new Object[0]);
        return result;
    }

    protected String noContentContentLength0$str() {
        return "RESTEASY003625: No content.  Content-Length is 0";
    }

    public final String noLongerASupportedContextParam(String paramName) {
        String result = String.format(this.noLongerASupportedContextParam$str(), new Object[]{paramName});
        return result;
    }

    protected String noLongerASupportedContextParam$str() {
        return "RESTEASY003630: %s is no longer a supported context param.  See documentation for more details";
    }

    public final String noMatchForAcceptHeader() {
        String result = String.format(this.noMatchForAcceptHeader$str(), new Object[0]);
        return result;
    }

    protected String noMatchForAcceptHeader$str() {
        return "RESTEASY003635: No match for accept header";
    }

    public final String noOutputStreamAllowed() {
        String result = String.format(this.noOutputStreamAllowed$str(), new Object[0]);
        return result;
    }

    protected String noOutputStreamAllowed$str() {
        return "RESTEASY003640: No output stream allowed";
    }

    public final String noPublicPathAnnotatedMethod(String resource, String method) {
        String result = MessageFormat.format(this.noPublicPathAnnotatedMethod$str(), new Object[]{resource, method});
        return result;
    }

    protected String noPublicPathAnnotatedMethod$str() {
        return "RESTEASY003645: No public @Path annotated method for {0}.{1}";
    }

    public final String noResourceMethodFoundForHttpMethod(String httpMethod) {
        String result = String.format(this.noResourceMethodFoundForHttpMethod$str(), new Object[]{httpMethod});
        return result;
    }

    protected String noResourceMethodFoundForHttpMethod$str() {
        return "RESTEASY003650: No resource method found for %s, return 405 with Allow header";
    }

    public final String noResourceMethodFoundForOptions() {
        String result = String.format(this.noResourceMethodFoundForOptions$str(), new Object[0]);
        return result;
    }

    protected String noResourceMethodFoundForOptions$str() {
        return "RESTEASY003655: No resource method found for options, return OK with Allow header";
    }

    public final String noTypeInformationForEntity() {
        String result = String.format(this.noTypeInformationForEntity$str(), new Object[0]);
        return result;
    }

    protected String noTypeInformationForEntity$str() {
        return "RESTEASY003660: No type information to extract entity with, use other getEntity() methods";
    }

    public final String notAllowedToReflectOnMethod(String methodName) {
        String result = String.format(this.notAllowedToReflectOnMethod$str(), new Object[]{methodName});
        return result;
    }

    protected String notAllowedToReflectOnMethod$str() {
        return "RESTEASY003665: Not allowed to reflect on method: %s";
    }

    public final String notEnoughPathParameters() {
        String result = String.format(this.notEnoughPathParameters$str(), new Object[0]);
        return result;
    }

    protected String notEnoughPathParameters$str() {
        return "RESTEASY003670: You did not supply enough values to fill path parameters";
    }

    public final String notValidInjectableType(String typeName) {
        String result = String.format(this.notValidInjectableType$str(), new Object[]{typeName});
        return result;
    }

    protected String notValidInjectableType$str() {
        return "RESTEASY003675: %s is not a valid injectable type for @Suspend";
    }

    public final String nullSubresource(URI uri) {
        String result = String.format(this.nullSubresource$str(), new Object[]{uri});
        return result;
    }

    protected String nullSubresource$str() {
        return "RESTEASY003680: Null subresource for path: %s.";
    }

    public final String nullValue() {
        String result = String.format(this.nullValue$str(), new Object[0]);
        return result;
    }

    protected String nullValue$str() {
        return "RESTEASY003685: null value";
    }

    public final String numberOfMatchedSegments() {
        String result = String.format(this.numberOfMatchedSegments$str(), new Object[0]);
        return result;
    }

    protected String numberOfMatchedSegments$str() {
        return "RESTEASY003690: Number of matched segments greater than actual";
    }

    public final String oddNumberOfCharacters() {
        String result = String.format(this.oddNumberOfCharacters$str(), new Object[0]);
        return result;
    }

    protected String oddNumberOfCharacters$str() {
        return "RESTEASY003695: Odd page of characters.";
    }

    public final String originNotAllowed(String origin) {
        String result = String.format(this.originNotAllowed$str(), new Object[]{origin});
        return result;
    }

    protected String originNotAllowed$str() {
        return "RESTEASY003700: Origin not allowed: %s";
    }

    public final String paramNull() {
        String result = String.format(this.paramNull$str(), new Object[0]);
        return result;
    }

    protected String paramNull$str() {
        return "RESTEASY003705: param was null";
    }

    public final String passedInValueNull() {
        String result = String.format(this.passedInValueNull$str(), new Object[0]);
        return result;
    }

    protected String passedInValueNull$str() {
        return "RESTEASY003710: A passed in value was null";
    }

    public final String pathNull() {
        String result = String.format(this.pathNull$str(), new Object[0]);
        return result;
    }

    protected String pathNull$str() {
        return "RESTEASY003715: path was null";
    }

    public final String pathParameterNotProvided(String param) {
        String result = String.format(this.pathParameterNotProvided$str(), new Object[]{param});
        return result;
    }

    protected String pathParameterNotProvided$str() {
        return "RESTEASY003720: path param %s has not been provided by the parameter map";
    }

    public final String patternNull() {
        String result = String.format(this.patternNull$str(), new Object[0]);
        return result;
    }

    protected String patternNull$str() {
        return "RESTEASY003725: pattern is null";
    }

    public final String qValueCannotBeGreaterThan1(String lang) {
        String result = String.format(this.qValueCannotBeGreaterThan1$str(), new Object[]{lang});
        return result;
    }

    protected String qValueCannotBeGreaterThan1$str() {
        return "RESTEASY003730: Accept-Language q value cannot be greater than 1.0 %s";
    }

    public final String quotedStringIsNotClosed(String header) {
        String result = String.format(this.quotedStringIsNotClosed$str(), new Object[]{header});
        return result;
    }

    protected String quotedStringIsNotClosed$str() {
        return "RESTEASY003735: Quoted string is not closed: %s";
    }

    public final String relParamNull() {
        String result = String.format(this.relParamNull$str(), new Object[0]);
        return result;
    }

    protected String relParamNull$str() {
        return "RESTEASY003740: rel param was null";
    }

    public final String removingHeaderIllegal() {
        String result = String.format(this.removingHeaderIllegal$str(), new Object[0]);
        return result;
    }

    protected String removingHeaderIllegal$str() {
        return "RESTEASY003745: Removing a header is illegal for an HttpServletResponse";
    }

    public final String requestMediaTypeNotUrlencoded() {
        String result = String.format(this.requestMediaTypeNotUrlencoded$str(), new Object[0]);
        return result;
    }

    protected String requestMediaTypeNotUrlencoded$str() {
        return "RESTEASY003750: Request media type is not application/x-www-form-urlencoded";
    }

    public final String requestWasAlreadyExecuted() {
        String result = String.format(this.requestWasAlreadyExecuted$str(), new Object[0]);
        return result;
    }

    protected String requestWasAlreadyExecuted$str() {
        return "RESTEASY003755: Request was already executed";
    }

    public final String resourceNull() {
        String result = String.format(this.resourceNull$str(), new Object[0]);
        return result;
    }

    protected String resourceNull$str() {
        return "RESTEASY003760: resource was null";
    }

    public final String responseIsClosed() {
        String result = String.format(this.responseIsClosed$str(), new Object[0]);
        return result;
    }

    protected String responseIsClosed$str() {
        return "RESTEASY003765: Response is closed.";
    }

    public final String responseIsCommitted() {
        String result = String.format(this.responseIsCommitted$str(), new Object[0]);
        return result;
    }

    protected String responseIsCommitted$str() {
        return "RESTEASY003770: Response is committed, can\'t handle exception";
    }

    public final String schemeSpecificPartNull() {
        String result = String.format(this.schemeSpecificPartNull$str(), new Object[0]);
        return result;
    }

    protected String schemeSpecificPartNull$str() {
        return "RESTEASY003775: schemeSpecificPart was null";
    }

    public final String segmentNull() {
        String result = String.format(this.segmentNull$str(), new Object[0]);
        return result;
    }

    protected String segmentNull$str() {
        return "RESTEASY003780: A segment is null";
    }

    public final String segmentsParameterNull() {
        String result = String.format(this.segmentsParameterNull$str(), new Object[0]);
        return result;
    }

    protected String segmentsParameterNull$str() {
        return "RESTEASY003785: segments parameter was null";
    }

    public final String shouldBeUnreachable() {
        String result = String.format(this.shouldBeUnreachable$str(), new Object[0]);
        return result;
    }

    protected String shouldBeUnreachable$str() {
        return "RESTEASY003790: Should be unreachable";
    }

    public final String sourceArrayCannotProcessBytes(int srcLen, int off, int len) {
        String result = MessageFormat.format(this.sourceArrayCannotProcessBytes$str(), new Object[]{Integer.valueOf(srcLen), Integer.valueOf(off), Integer.valueOf(len)});
        return result;
    }

    protected String sourceArrayCannotProcessBytes$str() {
        return "RESTEASY003795: Source array with length {0} cannot have offset of {1} and process {2} bytes.";
    }

    public final String sourceArrayCannotProcessFourBytes(int srcLen, int off) {
        String result = MessageFormat.format(this.sourceArrayCannotProcessFourBytes$str(), new Object[]{Integer.valueOf(srcLen), Integer.valueOf(off)});
        return result;
    }

    protected String sourceArrayCannotProcessFourBytes$str() {
        return "RESTEASY003800: Source array with length {0} cannot have offset of {1} and still process four bytes.";
    }

    public final String sourceArrayNull() {
        String result = String.format(this.sourceArrayNull$str(), new Object[0]);
        return result;
    }

    protected String sourceArrayNull$str() {
        return "RESTEASY003805: Source array was null.";
    }

    public final String streamWrappedBySignature() {
        String result = String.format(this.streamWrappedBySignature$str(), new Object[0]);
        return result;
    }

    protected String streamWrappedBySignature$str() {
        return "RESTEASY003810: Stream wrapped by Signature, cannot reset the stream without destroying signature";
    }

    public final String subresourceHasNoJaxRsAnnotations(String className) {
        String result = String.format(this.subresourceHasNoJaxRsAnnotations$str(), new Object[]{className});
        return result;
    }

    protected String subresourceHasNoJaxRsAnnotations$str() {
        return "RESTEASY003815: Subresource for target class has no jax-rs annotations.: %s";
    }

    public final String tClassParameterNull() {
        String result = String.format(this.tClassParameterNull$str(), new Object[0]);
        return result;
    }

    protected String tClassParameterNull$str() {
        return "RESTEASY003820: tClass parameter is null";
    }

    public final String tailingGarbage(String header) {
        String result = String.format(this.tailingGarbage$str(), new Object[]{header});
        return result;
    }

    protected String tailingGarbage$str() {
        return "RESTEASY003825: Tailing garbage: %s";
    }

    public final String templateParameterNull(String param) {
        String result = String.format(this.templateParameterNull$str(), new Object[]{param});
        return result;
    }

    protected String templateParameterNull$str() {
        return "RESTEASY003830: NULL value for template parameter: %s";
    }

    public final String templateValuesParamNull() {
        String result = String.format(this.templateValuesParamNull$str(), new Object[0]);
        return result;
    }

    protected String templateValuesParamNull$str() {
        return "RESTEASY003835: templateValues param null";
    }

    public final String titleParamNull() {
        String result = String.format(this.titleParamNull$str(), new Object[0]);
        return result;
    }

    protected String titleParamNull$str() {
        return "RESTEASY003840: title param was null";
    }

    public final String twoMethodsSameName(String method) {
        String result = String.format(this.twoMethodsSameName$str(), new Object[]{method});
        return result;
    }

    protected String twoMethodsSameName$str() {
        return "RESTEASY003845: there are two method named %s";
    }

    public final String typeParamNull() {
        String result = String.format(this.typeParamNull$str(), new Object[0]);
        return result;
    }

    protected String typeParamNull$str() {
        return "RESTEASY003850: type param was null";
    }

    public final String unableToCreateURI(String buf) {
        String result = String.format(this.unableToCreateURI$str(), new Object[]{buf});
        return result;
    }

    protected String unableToCreateURI$str() {
        return "RESTEASY003855: Unable to create URI: %s";
    }

    public final String unableToDecodeQueryString() {
        String result = String.format(this.unableToDecodeQueryString$str(), new Object[0]);
        return result;
    }

    protected String unableToDecodeQueryString$str() {
        return "RESTEASY003860: Unable to decode query string";
    }

    public final String unableToDetermineBaseClass() {
        String result = String.format(this.unableToDetermineBaseClass$str(), new Object[0]);
        return result;
    }

    protected String unableToDetermineBaseClass$str() {
        return "RESTEASY003865: Unable to determine base class from Type";
    }

    public final String unableToExtractParameter(String paramSignature, String strVal, AccessibleObject target) {
        String result = MessageFormat.format(this.unableToExtractParameter$str(), new Object[]{paramSignature, strVal, target});
        return result;
    }

    protected String unableToExtractParameter$str() {
        return "RESTEASY003870: Unable to extract parameter from http request: {0} value is \'{1}\' for {2}";
    }

    public final String unableToFindConstructor(String paramSignature, AccessibleObject target, String className) {
        String result = MessageFormat.format(this.unableToFindConstructor$str(), new Object[]{paramSignature, target, className});
        return result;
    }

    protected String unableToFindConstructor$str() {
        return "RESTEASY003875: Unable to find a constructor that takes a String param or a valueOf() or fromString() method for {0} on {1} for basetype: {2}";
    }

    public final String unableToFindContextualData(String className) {
        String result = String.format(this.unableToFindContextualData$str(), new Object[]{className});
        return result;
    }

    protected String unableToFindContextualData$str() {
        return "RESTEASY003880: Unable to find contextual data of type: %s";
    }

    public final String unableToFindInjectorFactory() {
        String result = String.format(this.unableToFindInjectorFactory$str(), new Object[0]);
        return result;
    }

    protected String unableToFindInjectorFactory$str() {
        return "RESTEASY003885: Unable to find InjectorFactory implementation.";
    }

    public final String unableToFindJaxRsResource(String path) {
        String result = String.format(this.unableToFindJaxRsResource$str(), new Object[]{path});
        return result;
    }

    protected String unableToFindJaxRsResource$str() {
        return "RESTEASY003890: Unable to find JAX-RS resource associated with path: %s.";
    }

    public final String unableToFindPublicConstructorForClass(String className) {
        String result = String.format(this.unableToFindPublicConstructorForClass$str(), new Object[]{className});
        return result;
    }

    protected String unableToFindPublicConstructorForClass$str() {
        return "RESTEASY003895: Unable to find a public constructor for class %s";
    }

    public final String unableToFindPublicConstructorForProvider(String className) {
        String result = String.format(this.unableToFindPublicConstructorForProvider$str(), new Object[]{className});
        return result;
    }

    protected String unableToFindPublicConstructorForProvider$str() {
        return "RESTEASY003900: Unable to find a public constructor for provider class %s";
    }

    public final String unableToFindTypeArguments(Class clazz) {
        String result = String.format(this.unableToFindTypeArguments$str(), new Object[]{clazz});
        return result;
    }

    protected String unableToFindTypeArguments$str() {
        return "RESTEASY003905: Unable to find type arguments of %s";
    }

    public final String unableToInstantiateClientExceptionMapper() {
        String result = String.format(this.unableToInstantiateClientExceptionMapper$str(), new Object[0]);
        return result;
    }

    protected String unableToInstantiateClientExceptionMapper$str() {
        return "RESTEASY003910: Unable to instantiate ClientExceptionMapper";
    }

    public final String unableToInstantiateContextObject(String key) {
        String result = String.format(this.unableToInstantiateContextObject$str(), new Object[]{key});
        return result;
    }

    protected String unableToInstantiateContextObject$str() {
        return "RESTEASY003915: Unable to instantiate context object %s";
    }

    public final String unableToInstantiateContextResolver() {
        String result = String.format(this.unableToInstantiateContextResolver$str(), new Object[0]);
        return result;
    }

    protected String unableToInstantiateContextResolver$str() {
        return "RESTEASY003920: Unable to instantiate ContextResolver";
    }

    public final String unableToInstantiateExceptionMapper() {
        String result = String.format(this.unableToInstantiateExceptionMapper$str(), new Object[0]);
        return result;
    }

    protected String unableToInstantiateExceptionMapper$str() {
        return "RESTEASY003925: Unable to instantiate ExceptionMapper";
    }

    public final String unableToInstantiateForm() {
        String result = String.format(this.unableToInstantiateForm$str(), new Object[0]);
        return result;
    }

    protected String unableToInstantiateForm$str() {
        return "RESTEASY003930: Unable to instantiate @Form class. No no-arg constructor.";
    }

    public final String unableToInstantiateInjectorFactory() {
        String result = String.format(this.unableToInstantiateInjectorFactory$str(), new Object[0]);
        return result;
    }

    protected String unableToInstantiateInjectorFactory$str() {
        return "RESTEASY003935: Unable to instantiate InjectorFactory implementation.";
    }

    public final String unableToInstantiateMessageBodyReader() {
        String result = String.format(this.unableToInstantiateMessageBodyReader$str(), new Object[0]);
        return result;
    }

    protected String unableToInstantiateMessageBodyReader$str() {
        return "RESTEASY003940: Unable to instantiate MessageBodyReader";
    }

    public final String unableToInstantiateMessageBodyWriter() {
        String result = String.format(this.unableToInstantiateMessageBodyWriter$str(), new Object[0]);
        return result;
    }

    protected String unableToInstantiateMessageBodyWriter$str() {
        return "RESTEASY003945: Unable to instantiate MessageBodyWriter";
    }

    public final String unableToParseDate(String dateValue) {
        String result = String.format(this.unableToParseDate$str(), new Object[]{dateValue});
        return result;
    }

    protected String unableToParseDate$str() {
        return "RESTEASY003950: Unable to parse the date %s";
    }

    public final String unableToParseLinkHeaderNoEndToLink(String value) {
        String result = String.format(this.unableToParseLinkHeaderNoEndToLink$str(), new Object[]{value});
        return result;
    }

    protected String unableToParseLinkHeaderNoEndToLink$str() {
        return "RESTEASY003955: Unable to parse Link header.  No end to link: %s";
    }

    public final String unableToParseLinkHeaderNoEndToParameter(String value) {
        String result = String.format(this.unableToParseLinkHeaderNoEndToParameter$str(), new Object[]{value});
        return result;
    }

    protected String unableToParseLinkHeaderNoEndToParameter$str() {
        return "RESTEASY003960: Unable to parse Link header.  No end to parameter: %s";
    }

    public final String unableToParseLinkHeaderTooManyLinks(String value) {
        String result = String.format(this.unableToParseLinkHeaderTooManyLinks$str(), new Object[]{value});
        return result;
    }

    protected String unableToParseLinkHeaderTooManyLinks$str() {
        return "RESTEASY003965: Unable to parse Link header. Too many links in declaration: %s";
    }

    public final String unableToResolveTypeVariable() {
        String result = String.format(this.unableToResolveTypeVariable$str(), new Object[0]);
        return result;
    }

    protected String unableToResolveTypeVariable$str() {
        return "RESTEASY003970: Unable to resolve type variable";
    }

    public final String unableToUnmarshalResponse(String attributeExceptionsTo) {
        String result = String.format(this.unableToUnmarshalResponse$str(), new Object[]{attributeExceptionsTo});
        return result;
    }

    protected String unableToUnmarshalResponse$str() {
        return "RESTEASY003975: Unable to unmarshall response for %s";
    }

    public final String unexpectedNumberSubclass(String classname) {
        String result = String.format(this.unexpectedNumberSubclass$str(), new Object[]{classname});
        return result;
    }

    protected String unexpectedNumberSubclass$str() {
        return "RESTEASY003977: Unexpected Number subclass: %s";
    }

    public final String unknownInterceptorPrecedence(String precedence) {
        String result = String.format(this.unknownInterceptorPrecedence$str(), new Object[]{precedence});
        return result;
    }

    protected String unknownInterceptorPrecedence$str() {
        return "RESTEASY003980: Unknown interceptor precedence: %s";
    }

    public final String unknownMediaTypeResponseEntity() {
        String result = String.format(this.unknownMediaTypeResponseEntity$str(), new Object[0]);
        return result;
    }

    protected String unknownMediaTypeResponseEntity$str() {
        return "RESTEASY003985: Unknown media type for response entity";
    }

    public final String unknownPathParam(String paramName, String path) {
        String result = MessageFormat.format(this.unknownPathParam$str(), new Object[]{paramName, path});
        return result;
    }

    protected String unknownPathParam$str() {
        return "RESTEASY003990: Unknown @PathParam: {0} for path: {1}";
    }

    public final String unknownStateListener() {
        String result = String.format(this.unknownStateListener$str(), new Object[0]);
        return result;
    }

    protected String unknownStateListener$str() {
        return "RESTEASY003995: Unknown state.  You have a Listener messing up what resteasy expects";
    }

    public final String unsupportedCollectionType(Class clazz) {
        String result = String.format(this.unsupportedCollectionType$str(), new Object[]{clazz});
        return result;
    }

    protected String unsupportedCollectionType$str() {
        return "RESTEASY004000: Unsupported collectionType: %s";
    }

    public final String unsupportedParameter(String parameter) {
        String result = String.format(this.unsupportedParameter$str(), new Object[]{parameter});
        return result;
    }

    protected String unsupportedParameter$str() {
        return "RESTEASY004005: Unsupported parameter: %s";
    }

    public final String uriNull() {
        String result = String.format(this.uriNull$str(), new Object[0]);
        return result;
    }

    protected String uriNull$str() {
        return "RESTEASY004010: URI was null";
    }

    public final String uriParamNull() {
        String result = String.format(this.uriParamNull$str(), new Object[0]);
        return result;
    }

    protected String uriParamNull$str() {
        return "RESTEASY004015: uri param was null";
    }

    public final String uriTemplateParameterNull() {
        String result = String.format(this.uriTemplateParameterNull$str(), new Object[0]);
        return result;
    }

    protected String uriTemplateParameterNull$str() {
        return "RESTEASY004020: uriTemplate parameter is null";
    }

    public final String uriValueNull() {
        String result = String.format(this.uriValueNull$str(), new Object[0]);
        return result;
    }

    protected String uriValueNull$str() {
        return "RESTEASY004025: URI value is null";
    }

    public final String userIsNotRegistered(String user) {
        String result = String.format(this.userIsNotRegistered$str(), new Object[]{user});
        return result;
    }

    protected String userIsNotRegistered$str() {
        return "RESTEASY004030: User is not registered: %s";
    }

    public final String valueNull() {
        String result = String.format(this.valueNull$str(), new Object[0]);
        return result;
    }

    protected String valueNull$str() {
        return "RESTEASY004035: A value was null";
    }

    public final String valueParamIsNull() {
        String result = String.format(this.valueParamIsNull$str(), new Object[0]);
        return result;
    }

    protected String valueParamIsNull$str() {
        return "RESTEASY004040: value param is null";
    }

    public final String valueParamWasNull() {
        String result = String.format(this.valueParamWasNull$str(), new Object[0]);
        return result;
    }

    protected String valueParamWasNull$str() {
        return "RESTEASY004045: value param was null";
    }

    public final String valuesParamIsNull() {
        String result = String.format(this.valuesParamIsNull$str(), new Object[0]);
        return result;
    }

    protected String valuesParamIsNull$str() {
        return "RESTEASY004050: values param is null";
    }

    public final String valuesParamWasNull() {
        String result = String.format(this.valuesParamWasNull$str(), new Object[0]);
        return result;
    }

    protected String valuesParamWasNull$str() {
        return "RESTEASY004055: values param was null";
    }

    public final String valuesParameterNull() {
        String result = String.format(this.valuesParameterNull$str(), new Object[0]);
        return result;
    }

    protected String valuesParameterNull$str() {
        return "RESTEASY004060: values parameter is null";
    }

    public final String variantListMustNotBeZero() {
        String result = String.format(this.variantListMustNotBeZero$str(), new Object[0]);
        return result;
    }

    protected String variantListMustNotBeZero$str() {
        return "RESTEASY004065: Variant list must not be zero";
    }

    public final String wrongPassword(String user) {
        String result = String.format(this.wrongPassword$str(), new Object[]{user});
        return result;
    }

    protected String wrongPassword$str() {
        return "RESTEASY004070: Wrong password for: %s";
    }
}
