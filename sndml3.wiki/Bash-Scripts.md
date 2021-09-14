# Directory Structure
The `bin` directory contains a collection of `bash` scripts for running SNDML. This structure allows for multiple SNDML configurations.

These scripts assume that the environment variable `SNDML_HOME` is the name of the directory where SNDML has been installed, and that within `$SNDML_HOME` the following structure exists.

| File or directory | Description |
| ----------------- | ----------- |
| pom.xml | Maven POM file |
| src/ | Location of Java source code |
| target/ | Location of JAR files |
| configs/ | `SNDML_CONFIGS` - Configurations directory (excluded in .gitignore) |
| configs/xxxxx/ | `SNDML_CONFIG` - Location of the xxxxx configuration |
| configs/xxxxx/.sndml_profile | `SNDML_PROFILE` - [[Connection Profile]] for the xxxxx configuration |
| configs/xxxxx/yaml/ | Location of YAML files |
| configs/xxxxx/metrics/ | Location of metrics files |
| configs/xxxxx/log/ | Location of log files |

The steps to create this structure are as follows.

1. In your `.profile` or `.bash_profile`, set the variable `SNDML_HOME` to the directory where you have installed the SNDML files.
1. Add `$SNDML_HOME/bin` to your `PATH`.
1. Within `$SNDML_HOME` create a directory named `configs`. Add `/configs/` to your `.gitignore` file. Or, as an alternative, you can create this directory elsewhere and define the variable `SNDML_CONFIGS`. If `$SNDML_CONFIGS` is undefined then it is inferred to be `$SNDML_HOME/configs`.
1. Within the configs directory create a subdirectory named `main` or `test` or `mydev` or whatever you want to call your first configuration. We will assume that you called it `mydev`.
1. Within this directory create a [[Connection Profile]] named `.sndml_profile`.
1. Create subdirectories named `yaml`, `metrics` and `log`.
1. Place your YAML files in the `yaml` directory. Each file should have a `.yaml` extension.

Note that you can have multiple configurations that share the same set of YAML files, in which case `yaml` might not be a physical directory, but rather a symbolic link to a directory of shared YAML files.

For the `bash` commands described below, the first argument is either the name of a config directory or the path of a config directory. If the config directory is the current directory, then you can use a period. For example, the following are equivalent.

#### Directory path

    sndml-fg $SNDML_HOME/configs/mydev incident_load

#### Current directory

    cd $SNDML_HOME/configs/mydev
    sndml-fg . incident_load

#### Directory name

    sndml-fg mydev incident_load

# Commands
### <tt>sndml-create <var>config</var> _tablename_</tt>
Print the SQL that would be used to create the SQL table. Note that the SQL is not actually executed. This is just so that you can see what the SQL would look like.
###### Example

    sndml-create . incident

### <tt>sndml-table <var>config</var> <var>tablename</var></tt>
Load a table. This is equivalent to the `-t` command line option.
#### Example

    sndml-table mydev incident

### <tt>sndml-fg <var>config</var> <var>yamlfile</var></tt>
Run the loader in the foreground with output sent to the console. 
The _yamlfile_ is assumed to be in the `yaml` directory, and a `.yaml` extension is automatically added.
##### Example
Load using `$SNDML_HOME/configs/mydev/yaml/incident_load.yaml`

    sndml-fg mydev incident_load

### <tt>sndml-bg <var>config</var> <var>yamlfile</var></tt>
Run the loader with all output redirected to a file in the `log` directory. 
This command is intended for use in `cron` jobs and other background contexts.
The _yamlfile_ is assumed to be in the `yaml` directory, and a `.yaml` extension is automatically added.

##### Example
Run the script `$SNDML_HOME/configs/mydev/yaml/incident_load.yaml` as a background process:

    nohup sndml-bg mydev incident_load &

Run the same script from a `cron` job:

    EXPORT SNDML_HOME=$HOME/sndml
    EXPORT PATH=$PATH:$SNDML_HOME/bin
    sndml-bg mydev incident_load

### <tt>sndml-cleanup <var>config</var> </tt>
Delete any log files older than 10 days.

### <tt>sndml-test</tt>
Run all JUnit tests. Refer to [[JUnit]] for more information.