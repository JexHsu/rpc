```markdown
# Basic RPC Implementation

This project provides a basic RPC implementation in Java. It includes modules for common functionality, client-side (consumer), server-side (provider), and the RPC framework itself.

## Project Structure

- **common**: Shared models and services.
- **consumer**: RPC client implementation.
- **provider**: RPC server implementation.
- **rpc**: Core RPC framework components.

## How to Build and Run

1. **Build**: Run the following command to build the project:
   ```sh
   mvn clean install
   ```

2. **Run Provider**:
   Open a terminal and execute:
   ```sh
   mvn exec:java -Dexec.mainClass="com.jexhsu.provider.BasicProvider"
   ```

3. **Run Consumer**:
   Open a second terminal and execute:
   ```sh
   mvn exec:java -Dexec.mainClass="com.jexhsu.consumer.BasicConsumer"
   ```

   **Note**: Make sure to run the provider and consumer in separate terminals.

## Reference

This project is inspired by the article [here](https://www.code-nav.cn/course/1768543954720022530/section/1768545847093518337?contentType=text&type=#heading-22).

## License

MIT License

## Contact

For questions, contact [jexhsu@gmail.com](mailto:jexhsu@gmail.com).
```
