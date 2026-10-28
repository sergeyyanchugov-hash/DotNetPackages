package com.example.dotnetpackages.data.model

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.*

object NuGetResponseSerializer : KSerializer<NuGetResponse> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("NuGetResponse") {
        element("data", ListSerializer(PackageDtoSerializer).descriptor)
    }
    override fun serialize(encoder: Encoder, value: NuGetResponse) {
        encoder.encodeStructure(descriptor) {
            encodeSerializableElement(descriptor, 0, ListSerializer(PackageDtoSerializer), value.data)
        }
    }
    override fun deserialize(decoder: Decoder): NuGetResponse {
        var data: List<PackageDto> = emptyList()
        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> data = decodeSerializableElement(descriptor, 0, ListSerializer(PackageDtoSerializer))
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
        }
        return NuGetResponse(data)
    }
}

@Serializable(with = NuGetResponseSerializer::class)
data class NuGetResponse(val data: List<PackageDto>)

object PackageDtoSerializer : KSerializer<PackageDto> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PackageDto") {
        element<String>("id")
        element<String>("version")
        element<String>("description", isOptional = true)
        element<String>("iconUrl", isOptional = true)
        element("versions", ListSerializer(PackageVersionDtoSerializer).descriptor, isOptional = true)
        element("dependencies", ListSerializer(String.serializer()).descriptor, isOptional = true)
    }
    override fun serialize(encoder: Encoder, value: PackageDto) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.id)
            encodeStringElement(descriptor, 1, value.version)
            value.description?.let { encodeStringElement(descriptor, 2, it) }
            value.iconUrl?.let { encodeStringElement(descriptor, 3, it) }
            encodeSerializableElement(descriptor, 4, ListSerializer(PackageVersionDtoSerializer), value.versions)
            encodeSerializableElement(descriptor, 5, ListSerializer(String.serializer()), value.dependencies)
        }
    }
    override fun deserialize(decoder: Decoder): PackageDto {
        var id = ""
        var version = ""
        var description: String? = null
        var iconUrl: String? = null
        var versions: List<PackageVersionDto> = emptyList()
        var dependencies: List<String> = emptyList()
        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> id = decodeStringElement(descriptor, 0)
                    1 -> version = decodeStringElement(descriptor, 1)
                    2 -> description = decodeStringElement(descriptor, 2)
                    3 -> iconUrl = decodeStringElement(descriptor, 3)
                    4 -> versions = decodeSerializableElement(descriptor, 4, ListSerializer(PackageVersionDtoSerializer))
                    5 -> dependencies = decodeSerializableElement(descriptor, 5, ListSerializer(String.serializer()))
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
        }
        return PackageDto(id, version, description, iconUrl, versions, dependencies)
    }
}

@Serializable(with = PackageDtoSerializer::class)
data class PackageDto(
    val id: String,
    val version: String,
    val description: String? = null,
    val iconUrl: String? = null,
    val versions: List<PackageVersionDto> = emptyList(),
    val dependencies: List<String> = emptyList()
)

object PackageVersionDtoSerializer : KSerializer<PackageVersionDto> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("PackageVersionDto") {
        element<String>("version")
    }
    override fun serialize(encoder: Encoder, value: PackageVersionDto) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.version)
        }
    }
    override fun deserialize(decoder: Decoder): PackageVersionDto {
        var version = ""
        decoder.decodeStructure(descriptor) {
            while (true) {
                when (val index = decodeElementIndex(descriptor)) {
                    0 -> version = decodeStringElement(descriptor, 0)
                    CompositeDecoder.DECODE_DONE -> break
                    else -> error("Unexpected index: $index")
                }
            }
        }
        return PackageVersionDto(version)
    }
}

@Serializable(with = PackageVersionDtoSerializer::class)
data class PackageVersionDto(val version: String)
