# Watching Directory

**application.properties**

watch.directory.source=./source
watch.directory.output=./output
watch.directory.parse.output.extension=JSON
watch.directory.events.kind=CREATE,MODIFY,OVERFLOW
watch.directory.file.supported.extensions=XML,JSON

```
@Bean(name = "watchConfiguration")
public WatchConfiguration getWatchConfiguration() {
Set<String> events = Arrays.asList(eventsKind)
.stream()
.filter(kind -> !Strings.isNullOrEmpty(kind)).map(kind -> {
	
	if ("ENTRY_".concat(kind).toUpperCase().equals(StandardWatchEventKinds.ENTRY_CREATE.name())) {
		return StandardWatchEventKinds.ENTRY_CREATE.name();
	}
	if ("ENTRY_".concat(kind).toUpperCase().equals(StandardWatchEventKinds.ENTRY_MODIFY.name())) {
		return StandardWatchEventKinds.ENTRY_MODIFY.name();
	}
	if (kind.toUpperCase().equals(StandardWatchEventKinds.OVERFLOW.name())) {
		return StandardWatchEventKinds.OVERFLOW.name();
	}
	return null;
})
.filter(kind -> !Strings.isNullOrEmpty(kind))
.collect(Collectors.toSet());

FileExtension extentsion = FileExtension.valueOf(outputExtention) != null
? FileExtension.valueOf(outputExtention)
: FileExtension.XML;

return new WatchConfiguration(Paths.get(source), Paths.get(output), events, new HashSet<String>(Arrays.asList(supportedExtentions)), extentsion);
}
```
  
```
@Bean(name = "watchDirectory")
@Autowired
public WatchDirectory getWatchDirectory() {
	return new WatchDirectory();
}
```