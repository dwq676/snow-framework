//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.jboss.resteasy.resteasy_jaxrs.i18n;

import java.io.Serializable;
import java.net.URL;
import java.util.Arrays;
import javax.ws.rs.core.MediaType;
import org.jboss.logging.BasicLogger;
import org.jboss.logging.DelegatingBasicLogger;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.jboss.resteasy.resteasy_jaxrs.i18n.LogMessages;

public class LogMessages_$logger extends DelegatingBasicLogger implements Serializable, BasicLogger, LogMessages {
    private static final long serialVersionUID = 1L;
    private static final String FQCN = LogMessages_$logger.class.getName();
    private static final String errorResumingFailedAsynchOperation = "RESTEASY002000: Error resuming failed async operation";
    private static final String failedExecutingError = "RESTEASY002005: Failed executing {0} {1}";
    private static final String failedToExecute = "RESTEASY002010: Failed to execute";
    private static final String failedToInvokeAsynchronously = "RESTEASY002015: Failed to invoke asynchronously";
    private static final String unhandledAsynchronousException = "RESTEASY002020: Unhandled asynchronous exception, sending back 500";
    private static final String unknownException = "RESTEASY002025: Unknown exception while executing {0} {1}";
    private static final String acceptExtensionsNotSupported = "RESTEASY002100: Accept extensions not supported.";
    private static final String ambiguousConstructorsFound = "RESTEASY002105: Ambiguity constructors are found in %s. More details please refer to http://jsr311.java.net/nonav/releases/1.1/spec/spec.html";
    private static final String attemptingToRegisterEmptyContracts = "RESTEASY002110: Attempting to register empty contracts for %s";
    private static final String attemptingToRegisterUnassignableContract = "RESTEASY002115: Attempting to register unassignable contract for %s";
    private static final String classNotFoundException = "RESTEASY002120: ClassNotFoundException: Unable to load builtin provider {0} from {1}";
    private static final String couldNotDeleteFile = "RESTEASY002125: Could not delete file \'%s\' for request: ";
    private static final String failedToParseRequest = "RESTEASY002130: Failed to parse request.";
    private static final String ignoringUnsupportedLocale = "RESTEASY002135: Ignoring unsupported locale: %s";
    private static final String JAXRSAnnotationsFoundAtNonPublicMethod = "RESTEASY002140: JAX-RS annotations found at non-public method: {0}.{1}(); Only public methods may be exposed as resource methods.";
    private static final String multipleMethodsMatch = "RESTEASY002142: Multiple resource methods match request {0}. Selecting one. Matching methods: {1}";
    private static final String noClassDefFoundErrorError = "RESTEASY002145: NoClassDefFoundError: Unable to load builtin provider {0} from {1}";
    private static final String noLongerSupported = "RESTEASY002150: %s is no longer supported.  Use a servlet 3.0 container and the ResteasyServletInitializer";
    private static final String providerClassAlreadyRegistered = "RESTEASY002155: Provider class {0} is already registered.  2nd registration is being ignored.";
    private static final String providerInstanceAlreadyRegistered = "RESTEASY002160: Provider instance {0} is already registered.  2nd registration is being ignored.";
    private static final String noValueOfMethodAvailable = "RESTEASY002165: No valueOf() method available for %s, trying constructor...";
    private static final String readerNotFound = "RESTEASY002170: A reader for {0} was not found. This provider is currently configured to handle only {1}";
    private static final String singletonClassAlreadyDeployed = "RESTEASY002172: Singleton {0} object class {1} already deployed. Singleton ignored.";
    private static final String useOfApplicationClass = "RESTEASY002175: The use of %s is deprecated, please use javax.ws.rs.Application as a context-param instead";
    private static final String addingClassResource = "RESTEASY002200: Adding class resource {0} from Application {1}";
    private static final String addingProviderClass = "RESTEASY002205: Adding provider class {0} from Application {1}";
    private static final String addingProviderSingleton = "RESTEASY002210: Adding provider singleton {0} from Application {1}";
    private static final String addingSingletonProvider = "RESTEASY002215: Adding singleton provider {0} from Application {1}";
    private static final String addingSingletonResource = "RESTEASY002220: Adding singleton resource {0} from Application {1}";
    private static final String deployingApplication = "RESTEASY002225: Deploying {0}: {1}";
    private static final String unableToCloseEntityStream = "RESTEASY002230: unable to close entity stream";
    private static final String creatingContextObject = "RESTEASY002300: Creating context object <{0} : {1}> ";
    private static final String failedExecutingDebug = "RESTEASY002305: Failed executing {0} {1}";
    private static final String failedToExecuteDebug = "RESTEASY002307: Failed to execute";
    private static final String inOneWay = "RESTEASY002310: IN ONE WAY!!!!!";
    private static final String pathInfo = "RESTEASY002315: PathInfo: %s";
    private static final String runningJob = "RESTEASY002320: RUNNING JOB!!!!";
    private static final String unableToRetrieveConfigDTDs = "RESTEASY002325: Unable to retrieve config: disableDTDs defaults to true";
    private static final String unableToRetrieveConfigExpand = "RESTEASY002330: Unable to retrieve config: expandEntityReferences defaults to false";
    private static final String unableToRetrieveConfigSecure = "RESTEASY002335: Unable to retrieve config: enableSecureProcessingFeature defaults to true";

    public LogMessages_$logger(Logger log) {
        super(log);
    }

    public final void errorResumingFailedAsynchOperation(Throwable cause) {
        super.log.logv(FQCN, Level.ERROR, cause, this.errorResumingFailedAsynchOperation$str(), new Object[0]);
    }

    protected String errorResumingFailedAsynchOperation$str() {
        return "RESTEASY002000: Error resuming failed async operation";
    }

    public final void failedExecutingError(String method, String path, Throwable cause) {
        super.log.logv(FQCN, Level.ERROR, cause, this.failedExecutingError$str(), method, path);
    }

    protected String failedExecutingError$str() {
        return "RESTEASY002005: Failed executing {0} {1}";
    }

    public final void failedToExecute(Throwable cause) {
        super.log.logf(FQCN, Level.ERROR, cause, this.failedToExecute$str(), new Object[0]);
    }

    protected String failedToExecute$str() {
        return "RESTEASY002010: Failed to execute";
    }

    public final void failedToInvokeAsynchronously(Throwable ignored) {
        super.log.logf(FQCN, Level.ERROR, ignored, this.failedToInvokeAsynchronously$str(), new Object[0]);
    }

    protected String failedToInvokeAsynchronously$str() {
        return "RESTEASY002015: Failed to invoke asynchronously";
    }

    public final void unhandledAsynchronousException(Throwable ignored) {
        super.log.logv(FQCN, Level.ERROR, ignored, this.unhandledAsynchronousException$str(), new Object[0]);
    }

    protected String unhandledAsynchronousException$str() {
        return "RESTEASY002020: Unhandled asynchronous exception, sending back 500";
    }

    public final void unknownException(String method, String path, Throwable cause) {
        super.log.logv(FQCN, Level.ERROR, cause, this.unknownException$str(), method, path);
    }

    protected String unknownException$str() {
        return "RESTEASY002025: Unknown exception while executing {0} {1}";
    }

    public final void acceptExtensionsNotSupported() {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.acceptExtensionsNotSupported$str(), new Object[0]);
    }

    protected String acceptExtensionsNotSupported$str() {
        return "RESTEASY002100: Accept extensions not supported.";
    }

    public final void ambiguousConstructorsFound(Class clazz) {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.ambiguousConstructorsFound$str(), clazz);
    }

    protected String ambiguousConstructorsFound$str() {
        return "RESTEASY002105: Ambiguity constructors are found in %s. More details please refer to http://jsr311.java.net/nonav/releases/1.1/spec/spec.html";
    }

    public final void attemptingToRegisterEmptyContracts(String className) {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.attemptingToRegisterEmptyContracts$str(), className);
    }

    protected String attemptingToRegisterEmptyContracts$str() {
        return "RESTEASY002110: Attempting to register empty contracts for %s";
    }

    public final void attemptingToRegisterUnassignableContract(String className) {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.attemptingToRegisterUnassignableContract$str(), className);
    }

    protected String attemptingToRegisterUnassignableContract$str() {
        return "RESTEASY002115: Attempting to register unassignable contract for %s";
    }

    public final void classNotFoundException(String line, URL url, Throwable cause) {
        super.log.logv(FQCN, Level.WARN, cause, this.classNotFoundException$str(), line, url);
    }

    @Override
    public void couldNotBind(String downloadDirectory) {

    }

    protected String classNotFoundException$str() {
        return "RESTEASY002120: ClassNotFoundException: Unable to load builtin provider {0} from {1}";
    }

    public final void couldNotDeleteFile(String path, Throwable cause) {
        super.log.logf(FQCN, Level.WARN, cause, this.couldNotDeleteFile$str(), path);
    }

    protected String couldNotDeleteFile$str() {
        return "RESTEASY002125: Could not delete file \'%s\' for request: ";
    }

    public final void failedToParseRequest(Throwable cause) {
        super.log.logf(FQCN, Level.WARN, cause, this.failedToParseRequest$str(), new Object[0]);
    }

    protected String failedToParseRequest$str() {
        return "RESTEASY002130: Failed to parse request.";
    }

    public final void ignoringUnsupportedLocale(String locale) {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.ignoringUnsupportedLocale$str(), locale);
    }

    @Override
    public void invalidFormat(String parameterName, String defaultValue) {

    }

    protected String ignoringUnsupportedLocale$str() {
        return "RESTEASY002135: Ignoring unsupported locale: %s";
    }

    public final void JAXRSAnnotationsFoundAtNonPublicMethod(String className, String method) {
        super.log.logv(FQCN, Level.WARN, (Throwable)null, this.JAXRSAnnotationsFoundAtNonPublicMethod$str(), className, method);
    }

    protected String JAXRSAnnotationsFoundAtNonPublicMethod$str() {
        return "RESTEASY002140: JAX-RS annotations found at non-public method: {0}.{1}(); Only public methods may be exposed as resource methods.";
    }

    public final void multipleMethodsMatch(String request, String[] methods) {
        super.log.logv(FQCN, Level.WARN, (Throwable)null, this.multipleMethodsMatch$str(), request, Arrays.toString(methods));
    }

    protected String multipleMethodsMatch$str() {
        return "RESTEASY002142: Multiple resource methods match request {0}. Selecting one. Matching methods: {1}";
    }

    public final void noClassDefFoundErrorError(String line, URL url, Throwable cause) {
        super.log.logv(FQCN, Level.WARN, cause, this.noClassDefFoundErrorError$str(), line, url);
    }

    protected String noClassDefFoundErrorError$str() {
        return "RESTEASY002145: NoClassDefFoundError: Unable to load builtin provider {0} from {1}";
    }

    public final void noLongerSupported(String param) {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.noLongerSupported$str(), param);
    }

    protected String noLongerSupported$str() {
        return "RESTEASY002150: %s is no longer supported.  Use a servlet 3.0 container and the ResteasyServletInitializer";
    }

    public final void providerClassAlreadyRegistered(String className) {
        super.log.logv(FQCN, Level.WARN, (Throwable)null, this.providerClassAlreadyRegistered$str(), className);
    }

    protected String providerClassAlreadyRegistered$str() {
        return "RESTEASY002155: Provider class {0} is already registered.  2nd registration is being ignored.";
    }

    public final void providerInstanceAlreadyRegistered(String className) {
        super.log.logv(FQCN, Level.WARN, (Throwable)null, this.providerInstanceAlreadyRegistered$str(), className);
    }

    protected String providerInstanceAlreadyRegistered$str() {
        return "RESTEASY002160: Provider instance {0} is already registered.  2nd registration is being ignored.";
    }

    public final void noValueOfMethodAvailable(String className) {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.noValueOfMethodAvailable$str(), className);
    }

    protected String noValueOfMethodAvailable$str() {
        return "RESTEASY002165: No valueOf() method available for %s, trying constructor...";
    }

    public final void readerNotFound(MediaType mediaType, String[] availableTypes) {
        super.log.logv(FQCN, Level.WARN, (Throwable)null, this.readerNotFound$str(), mediaType, Arrays.toString(availableTypes));
    }

    protected String readerNotFound$str() {
        return "RESTEASY002170: A reader for {0} was not found. This provider is currently configured to handle only {1}";
    }

    public final void singletonClassAlreadyDeployed(String type, String className) {
        super.log.logv(FQCN, Level.WARN, (Throwable)null, this.singletonClassAlreadyDeployed$str(), type, className);
    }

    protected String singletonClassAlreadyDeployed$str() {
        return "RESTEASY002172: Singleton {0} object class {1} already deployed. Singleton ignored.";
    }

    public final void useOfApplicationClass(String className) {
        super.log.logf(FQCN, Level.WARN, (Throwable)null, this.useOfApplicationClass$str(), className);
    }

    protected String useOfApplicationClass$str() {
        return "RESTEASY002175: The use of %s is deprecated, please use javax.ws.rs.Application as a context-param instead";
    }

    public final void addingClassResource(String className, Class clazz) {
        super.log.logv(FQCN, Level.INFO, (Throwable)null, this.addingClassResource$str(), className, clazz);
    }

    protected String addingClassResource$str() {
        return "RESTEASY002200: Adding class resource {0} from Application {1}";
    }

    public final void addingProviderClass(String className, Class clazz) {
        super.log.logv(FQCN, Level.INFO, (Throwable)null, this.addingProviderClass$str(), className, clazz);
    }

    protected String addingProviderClass$str() {
        return "RESTEASY002205: Adding provider class {0} from Application {1}";
    }

    public final void addingProviderSingleton(String className, Class application) {
        super.log.logv(FQCN, Level.INFO, (Throwable)null, this.addingProviderSingleton$str(), className, application);
    }

    protected String addingProviderSingleton$str() {
        return "RESTEASY002210: Adding provider singleton {0} from Application {1}";
    }

    public final void addingSingletonProvider(String className, Class application) {
        super.log.logv(FQCN, Level.INFO, (Throwable)null, this.addingSingletonProvider$str(), className, application);
    }

    protected String addingSingletonProvider$str() {
        return "RESTEASY002215: Adding singleton provider {0} from Application {1}";
    }

    public final void addingSingletonResource(String className, Class application) {
        super.log.logv(FQCN, Level.INFO, (Throwable)null, this.addingSingletonResource$str(), className, application);
    }

    protected String addingSingletonResource$str() {
        return "RESTEASY002220: Adding singleton resource {0} from Application {1}";
    }

    public final void deployingApplication(String className, Class clazz) {
        super.log.logv(FQCN, Level.INFO, (Throwable)null, this.deployingApplication$str(), className, clazz);
    }

    protected String deployingApplication$str() {
        return "RESTEASY002225: Deploying {0}: {1}";
    }

    public final void unableToCloseEntityStream(Throwable cause) {
        super.log.logf(FQCN, Level.INFO, cause, this.unableToCloseEntityStream$str(), new Object[0]);
    }

    protected String unableToCloseEntityStream$str() {
        return "RESTEASY002230: unable to close entity stream";
    }

    public final void creatingContextObject(String key, String value) {
        super.log.logv(FQCN, Level.DEBUG, (Throwable)null, this.creatingContextObject$str(), key, value);
    }

    protected String creatingContextObject$str() {
        return "RESTEASY002300: Creating context object <{0} : {1}> ";
    }

    public final void failedExecutingDebug(String method, String path, Throwable cause) {
        super.log.logv(FQCN, Level.DEBUG, cause, this.failedExecutingDebug$str(), method, path);
    }

    protected String failedExecutingDebug$str() {
        return "RESTEASY002305: Failed executing {0} {1}";
    }

    public final void failedToExecuteDebug(Throwable cause) {
        super.log.logf(FQCN, Level.DEBUG, cause, this.failedToExecuteDebug$str(), new Object[0]);
    }

    protected String failedToExecuteDebug$str() {
        return "RESTEASY002307: Failed to execute";
    }

    public final void inOneWay() {
        super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.inOneWay$str(), new Object[0]);
    }

    protected String inOneWay$str() {
        return "RESTEASY002310: IN ONE WAY!!!!!";
    }

    public final void pathInfo(String path) {
        super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.pathInfo$str(), path);
    }

    protected String pathInfo$str() {
        return "RESTEASY002315: PathInfo: %s";
    }

    public final void runningJob() {
        super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.runningJob$str(), new Object[0]);
    }

    protected String runningJob$str() {
        return "RESTEASY002320: RUNNING JOB!!!!";
    }

    public final void unableToRetrieveConfigDTDs() {
        super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.unableToRetrieveConfigDTDs$str(), new Object[0]);
    }

    protected String unableToRetrieveConfigDTDs$str() {
        return "RESTEASY002325: Unable to retrieve config: disableDTDs defaults to true";
    }

    public final void unableToRetrieveConfigExpand() {
        super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.unableToRetrieveConfigExpand$str(), new Object[0]);
    }

    protected String unableToRetrieveConfigExpand$str() {
        return "RESTEASY002330: Unable to retrieve config: expandEntityReferences defaults to false";
    }

    public final void unableToRetrieveConfigSecure() {
        super.log.logf(FQCN, Level.DEBUG, (Throwable)null, this.unableToRetrieveConfigSecure$str(), new Object[0]);
    }

    protected String unableToRetrieveConfigSecure$str() {
        return "RESTEASY002335: Unable to retrieve config: enableSecureProcessingFeature defaults to true";
    }
}
